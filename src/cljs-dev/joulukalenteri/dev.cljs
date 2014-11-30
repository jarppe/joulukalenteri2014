(ns joulukalenteri.dev
  (:require [figwheel.client :as fw]
            [weasel.repl :as ws-repl]
            [dommy.core :as d :refer-macros [sel sel1]]))

(js/console.log "dev init")

(fw/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback (fn [] (js/console.log "Reloaded")))

(ws-repl/connect "ws://localhost:9001" :verbose true)

(defn reset-hatches []
  (let [w (sel1 :#image-wrapper)]
    (doseq [h (sel :.hatch)]
      (.removeChild w h))))

(js/console.log "dev ready")
