(ns code.hashobject.views.lib
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)])
  ;(:require [code.hashobject.views.common :as common])
  )


(defn render [{metadata :meta projects :entries project :entry}]
  (:name project))
