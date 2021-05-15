(ns state.state)

(def records-state (atom []))

(defn add-record [record]
  (swap! records-state conj record))

(defn get-records-sort-color []
  @records-state)

(defn get-records-sort-birth [state]
  @records-state)

(defn get-records-sort-last [state]
  @records-state)