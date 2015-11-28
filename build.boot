(set-env!
  :source-paths #{"src"}
  :resource-paths #{"resources"}
  :dependencies '[[hiccup "1.0.5"]
                  [perun "0.2.1-SNAPSHOT"]
                  [hashobject/boot-s3 "0.1.2-SNAPSHOT"]
                  [clj-time "0.11.0"]
                  [pandeiro/boot-http "0.7.0"]
                  [org.martinklepsch/boot-gzip "0.1.2"]])

(require '[io.perun :refer :all]
         '[code.hashobject.views.lib :as lib-view]
         '[code.hashobject.views.index :as index-view]
         '[code.hashobject.projects :refer :all]
         '[pandeiro.boot-http :refer [serve]]
         '[hashobject.boot-s3 :refer :all]
         '[org.martinklepsch.boot-gzip :refer [gzip]])


(task-options!
  pom {:project 'code.hashobject.com
       :version "0.2.0"
       :description "Hashobject team open source corner."}
  s3-sync {
    :bucket "code.hashobject.com"
    :source "resources/public/"
    :access-key (System/getenv "AWS_ACCESS_KEY")
    :secret-key (System/getenv "AWS_SECRET_KEY")
    :options {"Cache-Control" "max-age=315360000, no-transform, public"}})


(deftask build-dev
  "Build dev version"
  []
  (comp (global-metadata)
        ;(markdown)
        (projects)
        (dump-meta)
        ;(draft)
        ;(ttr)
        (slug :slug-fn identity)
        (permalink)
        (dump-meta)
        ;(canonical-url)
        (render :renderer 'code.hashobject.views.lib/render)
        ;(collection :renderer 'code.hashobject.views.index/render :page "index.html")
        ))

(deftask build
  "Build prod version."
  []
  (comp (build-dev)
        (sitemap :filename "sitemap.xml")
        (rss :title "Hashobject" :description "Hashobject open source corner" :link "http://code.hashobject.com")
        (gzip :regex [#".html$" #".css$" #".js$"])
        (s3-sync)))

(deftask dev
  []
  (comp (watch)
        (build-dev)
        (serve :resource-root "public")))
