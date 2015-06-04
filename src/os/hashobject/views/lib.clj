(ns os.hashobject.views.lib
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)])
  (:require [os.hashobject.views.common :as common]))


(defn render [metadata]
  (html5 {:lang "en" :itemscope "" :itemtype "http://schema.org/WebPage"}
    [:head
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, user-scalable=no"}]
      [:title {:itemprop "name"} (:name metadata)]
      [:meta {:name "description" :content (:description metadata)}]
      [:link {:rel "shortcut icon" :href "/favicon.ico"}]
      [:link {:rel "publisher" :href "https://plus.google.com/118068495795214676039"}]
      [:link {:rel "author" :href "humans.txt"}]
      (include-css "/css/app.css")
      (include-css "http://fonts.googleapis.com/css?family=PT+Sans")
      (common/ga)]
    [:body
      [:div.off-canvas-wrap
       [:div.inner-wrap
         (common/header)
         [:div.row
            [:article.post.small-12.columns
              (str (:content metadata))]]
         (common/footer)]]]))