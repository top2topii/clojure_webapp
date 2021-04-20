(ns webapp.core
  (:require [webapp.server :refer [run-jetty]]))

(defn handler [request]
  {:status 201
   :headers {"Content-Type" "text/html;charset=utf-8"}
   :body (str
           "<p>method: " (name (:method request)) "</p>"
           "<p>path: " (:path request) "</p>")})

(defn -main [& args]
  (run-jetty handler))

