(ns main
  (:gen-class)
  (:require [clojure.pprint]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as s]))

(def cli-options
  ;; An option with a required argument
  [["-i" "--input <input file>" "File to read in and parse into records"
    :default "input.csv"
    :parse-fn #(str %)]
   ["-s" "--sort-type <color|date|name>" "Sort by Color, birthdate or last name"
    :default "color"
    :parse-fn #(str %)]
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

(defn -main [& args]
  (let [{:keys [options errors summary arguments]} (parse-opts args cli-options)
        {:keys [input sort-type]} options]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (do (println errors) (System/exit 1)))

    (let [f (clojure.java.io/file input)]
      (println f))))
