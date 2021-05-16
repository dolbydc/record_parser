(ns main
  (:gen-class)
  (:require [clojure.pprint]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as s]
            [ring.adapter.jetty :as ring]
            [output.output :as output]
            [parser.parser :refer [parse-file]]
            [state.state :refer [
              records-state
              add-record]]
            [web.routes :refer [routes]]))

(def cli-options
  ;; An option with a required argument
  [["-f" "--file <input file>" "File to read in and parse into records"
    :multi true
    :default []
    :update-fn conj]
   ["-s" "--sort-type <color|date|name>" "Sort by Color, birthdate or last name"
    :default ""
    :parse-fn #(str %)]
   ["-w" "--web" "Start webservice"
    :default 0
    :update-fn inc]
   ["-h" "--help"]])

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn usage
  [options-summary]
  (println "USAGE")
  (->> ["Read input file an parse into records that can be sorted"
        ""
        "Usage: record-parse [options]"
        ""
        "Options:"
        options-summary
        ]
       (s/join \newline)))

(defn start-web-app []
  (ring/run-jetty #'routes {:port 8080 :join? false}))

(defn get-action [sort-type web]
  (cond
    (= sort-type :color) #(println (output/color-sorted records-state))
    (= sort-type :date) #(println (output/birthdate-sorted records-state))
    (= sort-type :name) #(println (output/lastname-sorted records-state))
    (> web 0) start-web-app
    :else #(println "NO-OP")))

(defn -main [& args]
  (let [{:keys [options errors summary arguments]} (parse-opts args cli-options)
        {:keys [file sort-type web]} options
        action (get-action (keyword sort-type) web)]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (do (println errors) (System/exit 1)))

    (doseq [f file]
      (doseq [record (parse-file f)]
        (add-record records-state record)))

    (action)))
