(ns webapp.server
  (:import [org.eclipse.jetty.server Server ServerConnector]
           org.eclipse.jetty.server.handler.AbstractHandler
           [javax.servlet.http HttpServletRequest HttpServletResponse]))
           
(defn- build-request-map [^HttpServletRequest request]
  (let [query-string (.getQueryString request)]
    {:method (keyword (clojure.string/lower-case (.getMethod request)))
     :path (str (.getPathInfo request)
                (when query-string
                  (str "?" query-string)))}))

(defn- new-handler [handler]
  (proxy [AbstractHandler] []
    (handle [target base-request request ^HttpServletResponse response]
      (let [request-map (build-request-map request)
            {:keys [status body]} (handler request-map)]
        (.setStatus response status)
        (with-open [writer (.getWriter response)]
          (.print writer body))))))

(defn run-jetty [handler]
  (let [^Server server (Server.)
        ^ServerConnector connector (doto (ServerConnector. server)
                                     (.setPort 8080))]
    (doto server
      (.addConnector connector)
      (.setHandler (new-handler handler))
      (.start)
      (.join))))
