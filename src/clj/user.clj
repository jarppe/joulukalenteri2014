(ns user
  (:require [cemerick.piggieback :as piggieback]
            [weasel.repl.websocket :as weasel]))

(defn brepl []
  (println "Starting bREPL...")
  (piggieback/cljs-repl
   :repl-env (weasel/repl-env :ip "0.0.0.0" :port 9001))
  (println "bREPL ready"))

(println "Ready")
