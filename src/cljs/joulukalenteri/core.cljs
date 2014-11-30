(ns joulukalenteri.core
  (:require [dommy.core :as d :refer-macros [sel sel1]]
            [alandipert.storage-atom :refer [local-storage]]
            [joulukalenteri.hatch-pos :as pos]))

(def revealed-img "image/r.jpg")

(def opened (local-storage (atom {}) :opened))

(defn hatch-count []
  24
  #_(let [now (js/Date.)]
     (cond
       (> (.getYear now) 114)  24
       (< (.getMonth now) 11)  0
       :else (.getDate now))))

(defn hatches []
  (map (fn [{:keys [x y x2 y2]} n can-open?]
         {:x   x
          :y   y
          :w   (- x2 x)
          :h   (- y2 y)
          :n   n
          :opened?    (get @opened n false)
          :can-open?  can-open?})
       pos/hatch-positions
       (range 1 25)
       (concat (repeat (hatch-count) true)
               (repeat false))))

(defn open-hatch [{:keys [n x y]} div]
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

(defn close-hatch [{:keys [n]} div]
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
  (doseq [h (hatches)]
    (->hatch-div h)))
  
(-> js/window .-onload (set! run))
