(ns web.routes
  (:require [compojure.core :refer [routes GET POST]]
            [clojure.data.json :as json]
            [clojure.java.io :refer [reader]]
            [ring.util.response :refer [response]]
            [state.state :refer [add-record
                                 get-records-sort-color
                                 get-records-sort-birth
                                 get-records-sort-last]]
            [parser.parser :refer [parse-line-to-record]]))

(defn body->str [body]
  (let [rdr (reader body)]
    (slurp rdr)))

(defn handle-new-record [state req]
  (as-> (body->str (:body req)) record
    (parse-line-to-record record)
    (add-record state record))
  (response "OK"))

(defn setup-routes [state]
  (routes
   (POST "/records" req (handle-new-record state req))
   (GET "/records/color" [] (json/write-str (get-records-sort-color state)))
   (GET "/records/birthdate" [] (json/write-str (get-records-sort-birth state)))
   (GET "/records/name" [] (json/write-str (get-records-sort-last state)))))