(set-env!
  :source-paths #{"src"}
  :resource-paths #{"resources"}
  :dependencies '[[hiccup "1.0.5"]
                  [perun "0.1.3-SNAPSHOT"]
                  [hashobject/boot-s3 "0.1.2-SNAPSHOT"]
                  [clj-time "0.9.0"]
                  [pandeiro/boot-http "0.6.3-SNAPSHOT"]
                  [org.martinklepsch/boot-gzip "0.1.1"]])

(require '[os.hashobject.projects :refer :all])
(require '[os.hashobject.views.index :as index-view])
(require '[os.hashobject.views.lib :as project-view])
(require '[io.perun.permalink :refer :all])
(require '[io.perun.sitemap :refer :all])
(require '[io.perun.rss :refer :all])
(require '[io.perun.render :refer :all])
(require '[io.perun.collection :refer :all])

(require '[hashobject.boot-s3 :refer :all])
(require '[pandeiro.boot-http :refer :all])


(task-options!
  pom {:project 'os.hashobject.com
       :version "0.2.0"
       :description "Hashobject team open source corner."}
  s3-sync {
    :bucket "os.hashobject.com"
    :source "resources/public/"
    :access-key (System/getenv "AWS_ACCESS_KEY")
    :secret-key (System/getenv "AWS_SECRET_KEY")
    :options {"Cache-Control" "max-age=315360000, no-transform, public"}})


(deftask build-dev
  "Build dev version"
  []
  (comp (global-metadata)
        (markdown)
        (draft)
        (ttr)
        (slug)
        (permalink)
        (canonical-url)
        (render :renderer 'os.hashobject.views.lib/render)
        (collection :renderer 'os.hashobject.views.index/render :page "index.html")))

(deftask build
  "Build prod version."
  []
  (comp (build-dev)
        (sitemap :filename "sitemap.xml")
        (rss :title "Hashobject" :description "Hashobject open source corner" :link "http://os.hashobject.com")
        (gzip :regex [#".html$" #".css$" #".js$"])
        (s3-sync)))

(deftask dev
  []
  (comp (watch)
        (build-dev)
        (serve :resource-root "public")))
