(ns joulukalenteri.core
  (:require [dommy.core :as d :refer-macros [sel1]]))

(defonce app-state {1  {:state :disabled
                        :x 100 :y 100
                        :w  50 :h  50}
                    2  {:state :disabled
                        :x 200 :y 100
                        :w  50 :h  50}
                    3  {:state :disabled
                        :x 200 :y 200
                        :w  50 :h  50}})

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
  
(defn run []
  (js/console.log "run"))
  
(-> js/window .-onload (set! run))
