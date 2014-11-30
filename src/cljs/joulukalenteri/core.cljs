(ns joulukalenteri.core
  (:require [dommy.core :as d :refer-macros [sel sel1]]
            [alandipert.storage-atom :refer [local-storage]]
            [joulukalenteri.hatch-pos :as pos]
            [joulukalenteri.util :as u]))

(def revealed-img "image/r.png")

(def opened (local-storage (atom {}) :opened))

(defn hatches []
  (map (fn [pos n can-open?]
         (assoc pos
                :n n
                :opened? (get @opened n false)
                :can-open? can-open?))
       pos/hatch-positions
       (range 1 24)
       (concat (repeat (u/hatch-count) true)
               (repeat false))))

(defn open-hatch [{:keys [n x y]} div]
  (js/console.log "open-hatch" div)
  (let [img (js/document.createElement "img")]
    (-> img .-src              (set! revealed-img))
    (-> img .-style .-position (set! "relative"))
    (-> img .-style .-left     (set! (str "-" x "px")))
    (-> img .-style .-top      (set! (str "-" y "px")))
    (-> div
        (d/append! img)
        (d/add-class! "opened")
        (d/remove-class! "closed"))
    (swap! opened assoc n true)))

(defn close-hatch [hatch div]
  (js/console.log "close-hatch" div)
  (-> div
      (d/clear!)
      (d/add-class! "closed")
      (d/remove-class! "opened"))
  (swap! opened assoc n false))

(defn on-click-hatch [hatch div e]
  (.preventDefault e)
  (if (d/has-class? div "opened")
    (close-hatch hatch div)
    (open-hatch hatch div)))

(defn ->hatch-div [{:keys [n x y w h opened? can-open?] :as hatch}]
  (let [div (js/document.createElement "div")]
    (-> div .-className      (set! "hatch"))
    (-> div .-style .-left   (set! (str x "px")))
    (-> div .-style .-top    (set! (str y "px")))
    (-> div .-style .-width  (set! (str w "px")))
    (-> div .-style .-height (set! (str h "px")))
    (if can-open?
      (-> div
          (d/listen! :click (partial on-click-hatch hatch div))
          (d/add-class! "active")))
    (if opened?
      (open-hatch hatch div)
      (close-hatch hatch div))
    (d/append! (sel1 :#image-wrapper) div)))

(defn run []
  (js/console.log "run")
  (doseq [h (hatches)]
    (->hatch-div h)))
  
(-> js/window .-onload (set! run))
