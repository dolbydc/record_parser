(defproject record-parser "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374" :exclusions [org.clojure/tools.reader]]
                 [org.clojure/tools.cli "0.3.5"]]
  :clean-targets ^{:protect false} [:target-path "output"]
  :main main
  :source-paths ["src/clj"]
  :output-path "output")