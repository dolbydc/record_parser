(ns output.output
  (:require
   [state.state :refer [get-records-sort-color
                        get-records-sort-birth
                        get-records-sort-last]]
   [clojure.string :refer [join]]))

(defn record-to-csv [record]
  (-> (reduce (fn [accum [key value]] (str accum "," value)) "" record)
      (subs 1)))

(defn output-csv [sorted-records]
  (join "\n" (map record-to-csv sorted-records)))

(defn color-sorted [state]
  (output-csv (get-records-sort-color state)))

(defn birthdate-sorted [state]
  (output-csv (get-records-sort-birth state)))

(defn lastname-sorted [state]
  (output-csv (get-records-sort-last state)))