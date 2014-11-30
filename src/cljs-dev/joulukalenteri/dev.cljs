(ns joulukalenteri.dev
  (:require [dommy.core :as d :refer-macros [sel sel1]]))

(js/console.log "dev init")

(defn reset-hatches []
  (let [w (sel1 :#image-wrapper)]
    (doseq [h (sel :.hatch)]
      (.removeChild w h))))

