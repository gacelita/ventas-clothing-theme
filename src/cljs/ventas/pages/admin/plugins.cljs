(ns ventas.pages.admin.plugins
  (:require
   [ventas.routes :as routes]
   [ventas.i18n :refer [i18n]]
   [ventas.pages.admin :as admin]
   [ventas.components.base :as base]))

(defn page []
  [admin/skeleton
     [base/button {:onClick #(routes/go-to :admin.products.edit :id 0)}
      "Test"]])

(routes/define-route!
 :admin.plugins
 {:name (i18n ::page)
  :url "plugins"
  :component page})