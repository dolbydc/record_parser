(ns web.routes-test
  (:require
   [web.routes :refer [setup-routes]]
   [state.state :as state]
   [parser.parser :as parser]
   [clojure.java.io :as io]
   [clojure.test :refer [deftest is]]))

(def COLOR-SORTED-EXPECTED "[{\"last-name\":\"Johnson\",\"first-name\":\"John\",\"email\":\"jj@mail.com\",\"favorite-color\":\"blue\",\"date-of-birth\":\"11\\/27\\/1930\"},{\"last-name\":\"Palingo\",\"first-name\":\"Cindy\",\"email\":\"dc@mail.com\",\"favorite-color\":\"blue\",\"date-of-birth\":\"11\\/27\\/1900\"},{\"last-name\":\"Dolby\",\"first-name\":\"Donevan\",\"email\":\"dd@mail.com\",\"favorite-color\":\"green\",\"date-of-birth\":\"5\\/21\\/1901\"},{\"last-name\":\"Andersen\",\"first-name\":\"Mr\",\"email\":\"ma@mail.com\",\"favorite-color\":\"yellow\",\"date-of-birth\":\"11\\/27\\/1900\"}]")
(def BIRTHDATE-SORTED-EXPECTED "[{\"last-name\":\"Palingo\",\"first-name\":\"Cindy\",\"email\":\"dc@mail.com\",\"favorite-color\":\"blue\",\"date-of-birth\":\"11\\/27\\/1900\"},{\"last-name\":\"Andersen\",\"first-name\":\"Mr\",\"email\":\"ma@mail.com\",\"favorite-color\":\"yellow\",\"date-of-birth\":\"11\\/27\\/1900\"},{\"last-name\":\"Dolby\",\"first-name\":\"Donevan\",\"email\":\"dd@mail.com\",\"favorite-color\":\"green\",\"date-of-birth\":\"5\\/21\\/1901\"},{\"last-name\":\"Johnson\",\"first-name\":\"John\",\"email\":\"jj@mail.com\",\"favorite-color\":\"blue\",\"date-of-birth\":\"11\\/27\\/1930\"}]")
(def LASTNAME-SORTED-EXPECTED "[{\"last-name\":\"Palingo\",\"first-name\":\"Cindy\",\"email\":\"dc@mail.com\",\"favorite-color\":\"blue\",\"date-of-birth\":\"11\\/27\\/1900\"},{\"last-name\":\"Johnson\",\"first-name\":\"John\",\"email\":\"jj@mail.com\",\"favorite-color\":\"blue\",\"date-of-birth\":\"11\\/27\\/1930\"},{\"last-name\":\"Dolby\",\"first-name\":\"Donevan\",\"email\":\"dd@mail.com\",\"favorite-color\":\"green\",\"date-of-birth\":\"5\\/21\\/1901\"},{\"last-name\":\"Andersen\",\"first-name\":\"Mr\",\"email\":\"ma@mail.com\",\"favorite-color\":\"yellow\",\"date-of-birth\":\"11\\/27\\/1900\"}]")

(deftest test-POST
  (let [state (atom [])
        routes (setup-routes state)
        response (routes {:request-method :post
                          :uri "/records"
                          :body (io/input-stream (.getBytes "Dolby,Donevan,dd@mail.com,green,5/21/1901"))})]
    (is (= 200 (:status response)))
    (is (= "OK" (:body response)))
    (is (= (count @state) 1))))

(defn add-records-for-test [state]
  (state/add-record state (parser/parse-line-to-record "Dolby,Donevan,dd@mail.com,green,5/21/1901"))
  (state/add-record state (parser/parse-line-to-record "Palingo,Cindy,dc@mail.com,blue,11/27/1900"))
  (state/add-record state (parser/parse-line-to-record "Johnson,John,jj@mail.com,blue,11/27/1930"))
  (state/add-record state (parser/parse-line-to-record "Andersen,Mr,ma@mail.com,yellow,11/27/1900")))

(deftest test-GET-color-sorted
  (let [state (atom [])
        routes (setup-routes state)]
    (add-records-for-test state)
    (let [response (routes {:request-method :get
                            :uri "/records/color"})]
      (is (= 200 (:status response)))
      (is (= (compare (:body response) COLOR-SORTED-EXPECTED) 0)))))

(deftest test-GET-birthdate-sorted
  (let [state (atom [])
        routes (setup-routes state)]
    (add-records-for-test state)
    (let [response (routes {:request-method :get
                            :uri "/records/birthdate"})]
      (is (= 200 (:status response)))
      (is (= (compare (:body response) BIRTHDATE-SORTED-EXPECTED) 0)))))

(deftest test-GET-lastname-sorted
  (let [state (atom [])
        routes (setup-routes state)]
    (add-records-for-test state)
    (let [response (routes {:request-method :get
                            :uri "/records/name"})]
      (is (= 200 (:status response)))
      (is (= (compare (:body response) LASTNAME-SORTED-EXPECTED) 0)))))