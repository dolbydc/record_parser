(ns web.routes
  (:require [compojure.core :refer [defroutes GET]]))

(defroutes routes
  (GET "/" [] "<h2>Hello World</h2>"))