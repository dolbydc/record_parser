(ns parser.parser-test
  (:require [parser.parser :as parser]
            [clojure.test :refer [deftest is]]))

(deftest parse-line-to-record-test
  (let [line1 "Dolby,Donevan,dd@mail.com,green,5/21/1901"
        line2 "Dolby|Donevan|dd@mail.com|green|5/21/1901"
        line3 "Dolby Donevan dd@mail.com green 5/21/1901"
        rec1 (parser/parse-line-to-record line1)
        rec2 (parser/parse-line-to-record line2)
        rec3 (parser/parse-line-to-record line3)]
    (is (= (:last-name rec1) "Dolby"))
    (is (= (:first-name rec1) "Donevan"))
    (is (= (:last-name rec2) "Dolby"))
    (is (= (:first-name rec2) "Donevan"))
    (is (= (:last-name rec3) "Dolby"))
    (is (= (:first-name rec3) "Donevan"))))