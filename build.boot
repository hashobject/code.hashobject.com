(set-env!
  :source-paths #{"src"}
  :resource-paths #{"resources"}
  :dependencies '[[org.clojure/clojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [perun "0.1.0-SNAPSHOT"]
                 [clj-time "0.9.0"]
                 [pandeiro/boot-http "0.6.2"]
                 [jeluard/boot-notify "0.1.2" :scope "test"]])

(task-options!
  pom {:project 'os.hashobject.com
       :version "0.2.0"
       :description "Hashobject team open source corner."})

(require '[os.hashobject.projects :refer :all])
(require '[os.hashobject.views.index :as index-view])
(require '[os.hashobject.views.lib :as project-view])
(require '[io.perun.permalink :refer :all])
(require '[io.perun.sitemap :refer :all])
(require '[io.perun.rss :refer :all])
(require '[io.perun.render :refer :all])
(require '[io.perun.collection :refer :all])


(require '[pandeiro.boot-http :refer :all])
(require '[jeluard.boot-notify :refer [notify]])


(defn lib-filename
  "Default implementation for the `create-filename` task option"
  [file]
  "index.html")

(deftask build
  "Build site"
  []
  (comp (projects)
        (permalink)
        (render :renderer project-view/render)
        (collection :renderer index-view/render :page "index.html")
        (sitemap :filename "sitemap.xml")
        ;(rss :title "Hashobject" :description "Hashobject open source corner" :link "http://os.hashobject.com")
        (notify)))


