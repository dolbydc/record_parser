(defproject record-parser "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374" :exclusions [org.clojure/tools.reader]]
                 [org.clojure/tools.cli "1.0.206"]
                 [ring "1.9.0"]
                 [ring/ring-jetty-adapter "1.9.3"]
                 [compojure "1.6.2"]
                 [org.clojure/data.json "2.3.0"]
                 [clj-time "0.15.2"]]
  :clean-targets ^{:protect false} [:target-path "output"]
  :main main
  :plugins [
    [lein-cloverage "1.2.2"]
    [jonase/eastwood "0.4.2"]
    [lein-cljfmt "0.7.0"]]
  :source-paths ["src/clj"]
  :output-path "output")