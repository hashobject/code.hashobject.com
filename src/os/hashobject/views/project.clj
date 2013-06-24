(ns os.hashobject.views.project
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)])
  (:require [os.hashobject.views.common :as common]))


(defn index [metadata content]
  (html5 {:lang "en" :itemscope "" :itemtype "http://schema.org/BlogPosting"}
    [:head
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, user-scalable=no"}]
      (include-css "/css/app.css")
      (include-css "http://fonts.googleapis.com/css?family=PT+Sans")
      (common/ga)]
    [:body
     (common/header)
     [:div.row
       [:div.content
       (str content)
        ]]
     (common/footer)]))
