(ns semantic-ui-demo.views
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [semantic-ui-demo.subs :as subs]
            cljsjs.semantic-ui-react
            [cljs.reader :as reader]
            [semantic-ui-demo.events :as events]))

(def sui js/semanticUIReact)

(defn header []
  [:> sui.Menu {:inverted true}
   [:> sui.Container
    [:> sui.Menu.Item {:as "a" :header true :link true :href "/#simple-react"} "Semantic UI Demo"]
    [:> sui.Menu.Item {:as "a" :link true :href "/#simple"} "Home"]
    [:> sui.Dropdown {:item true :simple true :text "Dropdown"}
     [:> sui.Dropdown.Menu
      [:> sui.Dropdown.Item {:value (pr-str [::events/set-active-panel :simple-sui-panel])
                             :onClick #(-> %2 (aget "value") reader/read-string rf/dispatch)} "Simple"]
      [:> sui.Dropdown.Item {:value (pr-str [::events/set-active-panel :simple-sui-react-panel])
                             :onClick #(-> %2 (aget "value") reader/read-string rf/dispatch)} "Simple React"]
      [:> sui.Dropdown.Divider]
      [:> sui.Dropdown.Item
       [:i.dropdown.icon]
       [:span.text "Submenu"]
       [:> sui.Dropdown.Menu
        [:> sui.Dropdown.Item {:value   (pr-str [::events/set-active-panel :simple-sui-panel])
                               :onClick #(-> %2 (aget "value") reader/read-string rf/dispatch)} "Simple"]
        [:> sui.Dropdown.Item {:value   (pr-str [::events/set-active-panel :simple-sui-react-panel])
                               :onClick #(-> %2 (aget "value") reader/read-string rf/dispatch)} "Simple React"]]]]]]])


(defn simple-sui-panel []
  [:div
   [:h1.ui.header "Simple Semantic UI Panel"]
   [:a.ui.primary.button "Primary Brand Color"]
   [:a.ui.secondary.button "Secondary Brand Color"]
   [:div.ui.five.column.stackable.padded.grid
    [:div.red.column "Red"]
    [:div.orange.column "Orange"]
    [:div.olive.column "Olive"]
    [:div.green.column "Green"]
    [:div.teal.column "Teal"]
    [:div.blue.column "Blue"]
    [:div.violet.column "Violet"]
    [:div.purple.column "Purple"]
    [:div.brown.column "Brown"]
    [:div.black.column "Black"]]
   [:div.ui.segment "Segment"]
   [:div.ui.menu
    [:div.item "One"]
    [:div.item "Two"]]
   [:div.ui.message
    [:p "Message"]]
   [:div.ui.animated.button
    [:div.visible.content "Next"]
    [:div.hidden.content
     [:i.right.arrow.icon]]]
   [:div.ui.left.labeled.button
    [:a.ui.basic.right.pointing.label "2,048"]
    [:div.ui.teal.button
     [:i.red.heart.icon] "Like"]]
   [:div.ui.buttons
    [:button.ui.button "Cancel"]
    [:div.or]
    [:button.ui.positive.button "Save"]]
   [:button.ui.toggle.button "Vote"]
   [:div.ui.divider]
   [:button.ui.small.purple.button
    [:i.download.icon] "Download"]])

(defn dismissable-message []
  (let [state (r/atom {})]
    (fn [props & children]
      (if (-> @state :hidden? not)
        [:> sui.Message (merge props {:onDismiss #(swap! state update :hidden? not)})
         children]))))

(defn simple-sui-react-panel []
  [:div
   [:> sui.Header {:as :h1 :content "Simple Semantic UI React Panel"}]
   [:> sui.Button {:size :small :color :green}
    [:> sui.Icon {:name :download}] "Download"]
   [:> sui.Message {:success true :icon "thumbs up" :header "Nice job!" :content "Your profile is complete."}]
   [:> sui.Message
    [:> sui.Message.Header "Changes in Service"]
    [:p "We updated our policy."]]
   [:> sui.Message {:header "Changes in Service" :content "we updated our policy."}]
   [:> sui.Message {:header "New Site Features" :list ["You can have this." "And you can have that."]}]
   [:> sui.Message {:header "Ignored Prop Header"}
    [:> sui.Message.Content {:content "Ignored Message.Content"}
     [:> sui.Message.Header
      [:h4 "Message.Header children"]]
     [:p "Message.Content children"]]]
   [dismissable-message {:header "Dismissable Message" :content "A bit of a message..."}]
   #_[:> sui.Message {:header [:h4 "Prop header as hiccup"] :content [:h5 "Prop content as hiccup."]}]
   [dismissable-message nil
    [:> sui.Message.Content
     [:> sui.Message.Header
      [:h4.ui.header.red "Message.Header"]]
     [:p "Message.Content children for dismissable-message-ex."]]]
   [dismissable-message nil
    [:> sui.Message.Header
     [:h4.ui.header.red "Message.Header"]]
    [:p "Children for dismissable-message-ex."]]
   [:> sui.Accordion {:panels [{:title "What is a dog?" :content "..."}
                               {:title "What kinds are there?" :content "There should be more content here"}]}]
   [:> sui.Popup {:trigger (r/as-element [:> sui.Button {:icon "add"}]) :content "Add users to your feed."}]
   [:> sui.Segment.Group
    [:> sui.Responsive (merge (js->clj sui.Responsive.onlyMobile :keywordize-keys true) {:as sui.Segment}) "Mobile"]
    [:> sui.Responsive (merge (js->clj sui.Responsive.onlyTablet :keywordize-keys true) {:as sui.Segment}) "Tablet"]
    [:> sui.Responsive (merge (js->clj sui.Responsive.onlyComputer :keywordize-keys true) {:as sui.Segment}) "Computer"]
    [:> sui.Responsive (merge (js->clj sui.Responsive.onlyLargeScreen :keywordize-keys true) {:as sui.Segment}) "Large Screen"]
    [:> sui.Responsive (merge (js->clj sui.Responsive.onlyWidescreen :keywordize-keys true) {:as sui.Segment}) "Widescreen"]]])

(defn home-panel []
  (let [name (rf/subscribe [::subs/name])]
    [:div
     [:div
      (str "Hello from " @name ". This is the Home Page.")
      #_[:> button {:onClick #(js/alert "yo")} "Click"]
      #_[Button {:on-click #(js/alert "yowsa!")} "Click"]
      [:> sui.Button {:on-click #(js/alert "yowsa!!")} "Click!!"]

      [:div [:a {:href "#/about"} "go to About Page"]]
      [:hr]
      [:> sui.Responsive {:as "h1" :minWidth 320 :maxWidth 767 :content "Only Mobile" :icon "mobile"}]]]))

(defn about-panel []
  [:div "This is the About Page."
   [:div [:a {:href "#/"} "go to Home Page"]]])


(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :simple-sui-panel [simple-sui-panel]
    :simple-sui-react-panel [simple-sui-react-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (rf/subscribe [::subs/active-panel])]
    [:div
     [header]
     [:> sui.Container {:text true :style {:marginTop "2em"}}
      [show-panel @active-panel]]]))
