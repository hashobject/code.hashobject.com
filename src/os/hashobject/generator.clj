(ns os.hashobject.generator
  (:use clojure.java.io)
  (:require [markdown.core :as markdown]
            [endophile.core :as markdown-parser]
            [sitemap.core :as sitemap]
            [os.hashobject.views.index :as index-view]
            [os.hashobject.views.project :as project-view]))


(def projects ["translate" "sitemap" "rsa-signer" "mangopay"])

(defn project-to-clj [project-name]
  (into []
        (markdown-parser/to-clj
          (markdown-parser/mp
            (slurp (str "./" project-name "/README.md"))))))

(defn trim-if-not-nil [s]
  (if (clojure.string/blank? s)
    s
    (clojure.string/trim s)))


(defn generate-project-html [metadata]
  (println "generate project html" (:name metadata))
  (spit
   (str "./resources/public/" (:name metadata) "/index.html")
   (project-view/index metadata (:content metadata))))

(defn original-md-to-html-str [project-name]
  (markdown/md-to-html-string (slurp (str "./" project-name "/README.md"))))


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

