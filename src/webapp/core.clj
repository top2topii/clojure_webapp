(ns webapp.core
  (:require [webapp.server :refer [run-jetty]]))

(defn handler [request]
  {:status 201 :body (str "method:" (name (:method request)) "\n"
                       "path:" (:path request))})

(defn -main [& args]
  (run-jetty handler))

