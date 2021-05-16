(ns state.state)

(defn add-record [state record]
  (swap! state conj record))

(defn remove-sort-by-dates [records]
  (map #(dissoc % :sort-by-date) records))

(defn get-records-sort-color [state]
  (->> (sort-by (juxt :favorite-color :last-name) @state)
       (remove-sort-by-dates)))

(defn get-records-sort-birth [state]
  (->> (sort-by :sort-by-date @state)
       (remove-sort-by-dates)))

(defn get-records-sort-last [state]
  (->> (sort-by :last-name @state)
       reverse
       (remove-sort-by-dates)))