(ns semantic-ui-demo.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [semantic-ui-demo.events :as events]
            [semantic-ui-demo.routes :as routes]
            [semantic-ui-demo.views :as views]
            [semantic-ui-demo.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
