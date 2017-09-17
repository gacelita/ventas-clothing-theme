(ns ventas.themes.mariscosriasbajas.components.heading
  (:require [fqcss.core :refer [wrap-reagent]]
            [reagent.core :as reagent]))

(defn heading [text]
  (wrap-reagent
    [:div {:fqcss [::heading]}
      [:div {:fqcss [::line]}]
      [:div {:fqcss [::text]}
        [:h3 [:strong text]]]]))