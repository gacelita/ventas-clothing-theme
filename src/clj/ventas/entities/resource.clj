(ns ventas.entities.resource
  (:require [clojure.spec :as s]
            [clojure.test.check.generators :as gen]
            [com.gfredericks.test.chuck.generators :as gen']
            [ventas.database :as db]
            [ventas.database.entity :as entity]))

(s/def :resource/keyword keyword?)
(s/def :resource/name string?)
(s/def :resource/file
  (s/with-gen integer? #(gen/elements (map :id (entity/query :file)))))

(s/def :schema.type/resource
  (s/keys :req [:resource/keyword
                :resource/file]
          :opt [:resource/name]))


(defmethod entity/json :resource [entity]
  (-> entity
      (dissoc :type)
      (#(if-let [t (:file %1)]
          (assoc %1 :file (entity/json (entity/find t)))
          %1))))