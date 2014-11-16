(ns joulukalenteri.core
  (:require [dommy.core :as d :refer-macros [sel1]]
            [figwheel.client :as fw]
            [weasel.repl :as ws-repl]))

(defn dev []
  (fw/watch-and-reload
    :websocket-url "ws://localhost:3449/figwheel-ws"
    :jsload-callback (fn [] (js/console.log "Reloaded")))
  (ws-repl/connect "ws://localhost:9001" :verbose true))

(defn on-click-box [n e]
  (js/console.log "Clickz!" n)
  (.preventDefault e)
  (d/add-class! (.-target e) "opened")
  (-> e .-target .-style .-backgroundImage (set! "url('http://lorempixel.com/200/200/people')"))
  (js/console.log "target" (-> e .-target))
  (js/console.log "style" (-> e .-target .-style)))

(defn box [x y n]
  (let [e (js/document.createElement "div")]
    (-> e .-id (set! (str "box-" n)))
    (-> e .-className (set! "box"))
    (-> e .-style .-left (set! (str x "px")))
    (-> e .-style .-top (set! (str y "px")))
    (-> e .-innerHTML (set! (str n)))
    (-> e .-onclick (set! (partial on-click-box n)))
    e))

(defn add-box [x y n]
  (d/append!
    (sel1 :#image-wrapper)
    (box x y n)))
  
  (defn main []
    (js/console.log "Here we go!")
    (add-box 700 100 1)
    (add-box 350 150 24)
    #_(let [main-image (js/document.getElementById "image")]
       (js/console.log "imagez" main-image))
    (dev))

(-> js/window .-onload (set! main))
