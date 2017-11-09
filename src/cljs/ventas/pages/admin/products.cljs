(ns ventas.pages.admin.products
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.session :as session]
   [re-frame.core :as rf]
   [bidi.bidi :as bidi]
   [re-frame-datatable.core :as dt]
   [re-frame-datatable.views :as dt.views]
   [ventas.page :refer [pages]]
   [soda-ash.core :as sa]
   [ventas.utils :as utils]
   [ventas.utils.ui :as utils.ui]
   [ventas.pages.admin.skeleton :as admin.skeleton]
   [ventas.routes :as routes]
   [ventas.components.base :as base]
   [ventas.components.datatable :as datatable]
   [ventas.i18n :refer [i18n]]))

(def products-key :products)

(defn- action-column [_ row]
  [:div
   [base/button {:icon true :on-click #(routes/go-to :admin.products.edit :id (:id row))}
    [base/icon {:name "edit"}]]
   [base/button {:icon true :on-click #(rf/dispatch [:ventas/entities.remove (:id row)])}
    [base/icon {:name "remove"}]]])

(defn products-datatable []
  (rf/dispatch [:api/products.list {:success-fn #(rf/dispatch [:ventas/db [products-key] %])}])
  (fn []
    (let [id (keyword (gensym "products"))]
      [:div
       [dt/datatable id [:ventas/db [products-key]]
        [{::dt/column-key [:id]
          ::dt/column-label "#"
          ::dt/sorting {::dt/enabled? true}}

         {::dt/column-key [:name]
          ::dt/column-label (i18n ::name)}

         {::dt/column-key [:email]
          ::dt/column-label (i18n ::email)
          ::dt/sorting {::dt/enabled? true}}

         {::dt/column-key [:actions]
          ::dt/column-label (i18n ::actions)
          ::dt/render-fn action-column}]

        {::dt/pagination {::dt/enabled? true
                          ::dt/per-page 3}
         ::dt/table-classes ["ui" "table" "celled"]
         ::dt/empty-tbody-component (fn [] [:p (i18n ::no-products)])}]
       [:div.admin-products__pagination
        [datatable/pagination id [:ventas/db [products-key]]]]])))

(defn page []
  [admin.skeleton/skeleton
   [:div.admin__default-content.admin-products__page
    [products-datatable]
    [base/button {:onClick #(routes/go-to :admin.products.edit :id 0)} (i18n ::create-product)]]])

(routes/define-route!
 :admin.products
 {:name (i18n ::page)
  :url "products"
  :component page})