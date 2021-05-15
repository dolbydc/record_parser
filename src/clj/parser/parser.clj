(ns parser.parser)

(defrecord Record [last-name first-name email favorite-color date-of-birth])

(defn parse-line-to-record [line]
  (apply ->Record (clojure.string/split line #" |,|\|")))

(defn parse-file [filename]
  (with-open [f (clojure.java.io/reader filename)]
    (let [file-lines (line-seq f)]
      (mapv parse-line-to-record file-lines))))

