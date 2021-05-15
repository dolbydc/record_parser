(ns parser.parser
  (:require
    [clj-time.core :as t]
    [clojure.string :refer [split]]
    [clojure.java.io :refer [reader]]))

(defrecord Record [last-name first-name email favorite-color date-of-birth sort-by-date])

(defn parse-line-to-record [line]
  (let [[last first email color date] (split line #" |,|\|")
        [mm dd yyyy] (split date #"/")]
    (apply ->Record
      [last first email color date (t/date-time
        (Integer/parseInt yyyy)
        (Integer/parseInt mm)
        (Integer/parseInt dd))])))

(defn parse-file [filename]
  (with-open [f (reader filename)]
    (let [file-lines (line-seq f)]
      (mapv parse-line-to-record file-lines))))

