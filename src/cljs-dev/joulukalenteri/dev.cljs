(ns joulukalenteri.dev
  (:require [figwheel.client :as fw]
            [weasel.repl :as ws-repl]))

(js/console.log "dev init")

(fw/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback (fn [] (js/console.log "Reloaded")))

(ws-repl/connect "ws://localhost:9001" :verbose true)

(js/console.log "dev ready")
