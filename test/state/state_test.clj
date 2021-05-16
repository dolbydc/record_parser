(ns state.state-test
  (:require [state.state :as state]
            [parser.parser :as parser]
            [clojure.test :refer [deftest is]]))

(deftest state-tests
  (let [state (atom [])]
    (state/add-record state (parser/parse-line-to-record "Dolby,Donevan,dd@mail.com,green,5/21/1901"))
    (state/add-record state (parser/parse-line-to-record "Palingo,Cindy,dc@mail.com,blue,11/27/1900"))
    (state/add-record state (parser/parse-line-to-record "Johnson,John,jj@mail.com,blue,11/27/1930"))
    (state/add-record state (parser/parse-line-to-record "Andersen,Mr,ma@mail.com,yellow,11/27/1900"))
    (is (= (count @state) 4))
    (let [color-sorted (state/get-records-sort-color state)
          birth-sorted (state/get-records-sort-birth state)
          name-sorted (state/get-records-sort-last state)]
        ; color storted check
      (is (= (vec (map :last-name color-sorted)) ["Johnson" "Palingo" "Dolby" "Andersen"]))
      (is (= (vec (map :last-name birth-sorted)) ["Palingo" "Andersen" "Dolby" "Johnson"]))
      (is (= (vec (map :last-name name-sorted)) ["Palingo" "Johnson" "Dolby" "Andersen"])))))