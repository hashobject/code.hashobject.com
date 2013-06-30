(ns os.hashobject.generator
  (:use clojure.java.io)
  (:require [sitemap.core :as sitemap]
            [pygdown.core :as py]
            [os.hashobject.views.index :as index-view]
            [os.hashobject.views.project :as project-view]))


(def projects ["translate" "sitemap" "rsa-signer" "mangopay"])



(defn generate-project-html [metadata]
  (println "generate project html" (:name metadata))
  (spit
   (str "./resources/public/" (:name metadata) "/index.html")
   (project-view/index metadata (:content metadata))))

(defn original-md-to-html-str [project-name]
  (py/to-html (slurp (str "./" project-name "/README.md"))))


(defn process-project [project-name]
  (let [metadata {}
        content (original-md-to-html-str project-name)]
    (assoc metadata :name project-name
                    :content content)))


(defn process-projects []
  (for [project projects]
    (process-project project)))


(defn generate-projects []
  (let [projects (process-projects)]
    (for [project projects]
       (generate-project-html project))))

(defn generate-index []
  (let [projects (process-projects)]
    (println "projects" projects)
    (spit (str "./resources/public/index.html")
          (index-view/index projects))))





(defn projects-sitemap-definitions []
  (let [projects (process-projects)]
    (for [project projects]
      {:loc (str "http://os.hashobject.com/" (:name project))
       :lastmod "2013-06-27"
       :changefreq "weekly"
       :priority 0.8})))

(defn generate-sitemap []
  (let [projects-pages (projects-sitemap-definitions)
        all-pages (conj projects-pages
                        {:loc (str "http://os.hashobject.com/")
                         :lastmod "2013-06-27"
                         :changefreq "daily"
                         :priority 1.0})]
        (sitemap/generate-sitemap-and-save "./resources/public/sitemap.xml" all-pages)))