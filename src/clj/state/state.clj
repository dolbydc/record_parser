(ns state.state)

(def records-state (atom []))

(defn add-record [record]
  (swap! records-state conj record))

(defn remove-sort-by-dates [records]
  (map #(dissoc % :sort-by-date) records))

(defn get-records-sort-color []
  (->> (sort-by (juxt :favorite-color :last-name) @records-state)
       (remove-sort-by-dates)))

(defn get-records-sort-birth []
  (->> (sort-by :sort-by-date @records-state)
       (remove-sort-by-dates)))

(defn get-records-sort-last []
  (->> (sort-by :last-name @records-state)
       reverse
       (remove-sort-by-dates)))