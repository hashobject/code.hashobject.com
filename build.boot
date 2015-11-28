(set-env!
  :source-paths #{"src"}
  :resource-paths #{"resources"}
  :dependencies '[[perun "0.2.1-SNAPSHOT"]
                  [hiccup "1.0.5"]
                  [pygdown "0.1.5"]
                  [clj-time "0.11.0"]
                  [confetti "0.1.0-SNAPSHOT"]
                  [pandeiro/boot-http "0.7.0"]
                  [org.martinklepsch/boot-gzip "0.1.2"]])

(require '[io.perun :refer :all]
         '[code.hashobject.views.lib :as lib-view]
         '[code.hashobject.views.index :as index-view]
         '[pandeiro.boot-http :refer [serve]]
         '[org.martinklepsch.boot-gzip :refer [gzip]]
         '[confetti.boot-confetti :refer [create-site sync-bucket]]
         '[io.perun.core :as perun]
         '[boot.core :as boot]
         '[boot.util       :as u]
         '[clojure.java.io :as io]
         '[clojure.edn     :as edn]
         '[pygdown.core    :as py])


(task-options!
  pom {:project 'code.hashobject.com
       :version "0.2.0"
       :description "Hashobject team open source corner."}
  sync-bucket {:creds {
    :access-key (System/getenv "AWS_ACCESS_KEY")
    :secret-key (System/getenv "AWS_SECRET_KEY")}}
  create-site {:creds {
    :access-key (System/getenv "AWS_ACCESS_KEY")
    :secret-key (System/getenv "AWS_SECRET_KEY")}}
  sync-bucket {
    ; :dry-run true
    :bucket "code-hashobject-com-confetti-static-si-sitebucket-1d00u2838ns0u"
    :creds {
      :access-key (System/getenv "AWS_ACCESS_KEY")
      :secret-key (System/getenv "AWS_SECRET_KEY")}
  })


(defn parse-project-file [file]
  (-> file :full-path io/file slurp edn/read-string))

(defn readme-to-html [project-name fileset]
  (let [readme-path (re-pattern (str project-name "/README.md"))
        md-files (map add-filedata (->> fileset boot/user-files (boot/by-re [readme-path])))
        readme  (-> md-files first :full-path io/file slurp)]
    (py/to-html readme)))

(defn process-file [file fileset]
  (let [[_ project-name version & info] (parse-project-file file)
        info-map (merge file (apply hash-map info))
        readme (readme-to-html project-name fileset)]
    (u/info (prn-str info))
    (assoc info-map :name project-name
                    :filename project-name
                    :content readme)))

(boot/deftask projects
  "Parse projects information"
  []
  (boot/with-pre-wrap fileset
    (let [projects-files (map add-filedata
            (->> fileset boot/user-files (boot/by-name ["project.clj"])))
          parsed-files (map #(process-file % fileset) projects-files)]
      (u/info "Parsed %s projects files\n" (count parsed-files))
      (perun/set-meta fileset parsed-files))))


(deftask deploy []
  (comp
    ; (sift :move {#"^sitemap/" "public/sitemap/"
    ;                 #"^time-to-read/" "public/time-to-read/"
    ;                 #"^translate/" "public/translate/"})
        (sift :include #{#"^public/"})
        (sift :move {#"^public/" ""})
        (sync-bucket)
    ))

(deftask build-dev
  "Build dev version"
  []
  (comp ;(global-metadata)
        (projects)
        (dump-meta)
        (slug :slug-fn identity)
        (permalink)
        (dump-meta)
        (canonical-url)
        (render :renderer 'code.hashobject.views.lib/render)
        (collection :renderer 'code.hashobject.views.index/render :page "index.html")
        ))

(deftask build
  "Build prod version"
  []
  (comp (build-dev)
        ;(inject-scripts :scripts #{"ga.js"})
        ;(sitemap :filename "sitemap.xml")
        ;(rss :title "Hashobject" :description "Hashobject open source corner" :link "http://code.hashobject.com")
        ;(gzip :regex [#".html$" #".css$" #".js$"])
        (deploy)
        ))

(deftask dev
  []
  (comp (watch)
        (build-dev)
        (serve :resource-root "public")))
