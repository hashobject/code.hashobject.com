(ns code.hashobject.views.index
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)])
  (:require [code.hashobject.views.common :as common]))

(defn render-project [project]
  [:li.item
   [:a.title {:href (str "/" (:name project) "/")} (:name project)]
   [:p (:description project)]])


(defn render [{metadata :meta projects :entries}]
  (html5 {:lang "en" :itemscope "" :itemtype "http://schema.org/WebPage"}
    [:head
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, user-scalable=no"}]
      [:meta {:itemprop "author" :name "author" :content "Hashobject (team@hashobject.com)"}]
      [:meta {:name "keywords" :itemprop "keywords" :content "hashobject, open source, clojure, development"}]
      [:meta {:name "description" :itemprop "description" :content "Hashobject - software engineering, design and application development"}]
      [:title {:itemprop "name"} "Hashobject team open source corner"]
      [:link {:rel "shortcut icon" :href "/favicon.ico"}]
      [:link {:rel "publisher" :href "https://plus.google.com/118068495795214676039"}]
      [:link {:rel "author" :href "humans.txt"}]
      (include-css "/css/app.css")
      (include-css "https://fonts.googleapis.com/css?family=PT+Sans")
     ]
    [:body
       (common/header)
       [:div.row.content
         [:ul.items.columns.small-12
          (for [project projects] (render-project project))]]
       (common/footer)]))
