(ns web.routes
  (:require [compojure.core :refer [defroutes GET POST]]
            [clojure.data.json :as json]
            [clojure.java.io :refer [reader]]
            [state.state :refer [
              records-state
              add-record
              get-records-sort-color
              get-records-sort-birth
              get-records-sort-last
            ]]
            [parser.parser :refer [parse-line-to-record]]))

(defn body->str [body]
  (let [rdr (reader body)]
    (slurp rdr)))

(defn handle-new-record [req]
  (as-> (body->str (:body req)) record
        (parse-line-to-record record)
        (add-record records-state record))
  "OK")

(defroutes routes
  (POST "/records" req (handle-new-record req))
  (GET "/records/color" [] (json/write-str (get-records-sort-color records-state)))
  (GET "/records/birthdate" [] (json/write-str (get-records-sort-birth records-state)))
  (GET "/records/name" [] (json/write-str (get-records-sort-last records-state))))