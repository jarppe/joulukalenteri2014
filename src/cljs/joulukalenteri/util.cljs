(ns joulukalenteri.util)

(defn hatch-count []
  3
  #_(let [now (js/Date.)]
     (cond
       (> (.getYear now) 114)  24
       (< (.getMonth now) 11)  0
       :else (.getDate now))))
