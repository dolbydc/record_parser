(ns output.output-test
  (:require [output.output :as output]
            [parser.parser :as parser]
            [state.state :as state]
            [clojure.test :refer [deftest is]]))

(deftest record-to-csv-test
  (let [line "Dolby|Donevan|dd@mail.com|green|5/21/1901"
        rec (parser/parse-line-to-record line)]
    (is (= (compare (output/record-to-csv rec) "Dolby,Donevan,dd@mail.com,green,5/21/1901")))))

(def COLOR_SORTED "Johnson,John,jj@mail.com,blue,11/27/1930
Palingo,Cindy,dc@mail.com,blue,11/27/1900
Dolby,Donevan,dd@mail.com,green,5/21/1901
Andersen,Mr,ma@mail.com,yellow,11/27/1900")

(def BIRTHDATE_SORTED "Palingo,Cindy,dc@mail.com,blue,11/27/1900
Andersen,Mr,ma@mail.com,yellow,11/27/1900
Dolby,Donevan,dd@mail.com,green,5/21/1901
Johnson,John,jj@mail.com,blue,11/27/1930")

(def LASTNAME_SORTED "Palingo,Cindy,dc@mail.com,blue,11/27/1900
Johnson,John,jj@mail.com,blue,11/27/1930
Dolby,Donevan,dd@mail.com,green,5/21/1901
Andersen,Mr,ma@mail.com,yellow,11/27/1900")

(deftest color-sorted-test
  (let [local-state (atom [])]
    (state/add-record local-state (parser/parse-line-to-record "Dolby,Donevan,dd@mail.com,green,5/21/1901"))
    (state/add-record local-state (parser/parse-line-to-record "Palingo,Cindy,dc@mail.com,blue,11/27/1900"))
    (state/add-record local-state (parser/parse-line-to-record "Johnson,John,jj@mail.com,blue,11/27/1930"))
    (state/add-record local-state (parser/parse-line-to-record "Andersen,Mr,ma@mail.com,yellow,11/27/1900"))
    (is (= (compare (output/color-sorted local-state) COLOR_SORTED) 0))
    (is (= (compare (output/birthdate-sorted local-state) BIRTHDATE_SORTED) 0))
    (is (= (compare (output/lastname-sorted local-state) LASTNAME_SORTED) 0))))