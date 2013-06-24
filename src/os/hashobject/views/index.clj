(ns os.hashobject.views.index
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)])
  (:require [os.hashobject.views.common :as common]))



(defn render-project [project]
  [:li.project
   [:a {:href (:name project)} (:name project)]])


(defn index [projects]
  (html5 {:lang "en" :itemtype "http://schema.org/Site"}
    [:head
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, user-scalable=no"}]
      [:meta {:itemprop "author" :name "author" :content "HashObject (team@hashobject.com)"}]
      [:meta {:name "keywords" :itemprop "keywords" :content "hashobject, blog, clojure, development, heroku, amazon route 53, aws"}]
      [:meta {:name "description" :itemprop "description" :content "HashObject - software engineering, design and application development"}]
      [:title "HashObject team open source corner"]
      [:link {:rel "publisher" :href "https://plus.google.com/118068495795214676039"}]
      (include-css "/css/app.css")
      (include-css "http://fonts.googleapis.com/css?family=PT+Sans")
      (common/ga)
     ]
    [:body
     (common/header)
     [:div.row
      [:div.content
       [:ul.projects.columns.large-12
        (for [project projects] (render-project project))]]]

     (common/footer)]))
