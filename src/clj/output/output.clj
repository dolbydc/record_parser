(ns output.output
  (:require [state.state :refer [
      get-records-sort-color
      get-records-sort-birth
      get-records-sort-last]]))

(defn record-to-csv [record]
  (-> (reduce (fn [accum [key value]] (str accum "," value)) "" record)
      (subs 1)))

(defn color-sorted []
  (doseq [r (get-records-sort-color)]
    (println (record-to-csv r))))

(defn birthdate-sorted []
  (doseq [r (get-records-sort-birth)]
    (println (record-to-csv r))))

(defn lastname-sorted []
  (doseq [r (get-records-sort-last)]
    (println (record-to-csv r))))
