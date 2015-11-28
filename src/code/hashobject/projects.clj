(set-env!
  :dependencies '[[org.clojure/clojure "1.7.0"]
                  [pygdown "0.1.5"]
                  ;[perun "0.2.1-SNAPSHOT"]
                  ])

(ns code.hashobject.projects
  {:boot/export-tasks true}
  (:require [boot.core       :as boot]
            [boot.util       :as u]
            [io.perun.core   :as perun]
            [clojure.java.io :as io]
            [clojure.edn     :as edn]
            [pygdown.core    :as py]))


(def ^:private
  +defaults+ {:datafile "meta.edn"})

(defn add-filedata-info [f]
  (let [tmpfile  (boot/tmp-file f)
        filename (.getName tmpfile)
        tmp-path (boot/tmp-path f)]
    {; filename with extension
     :filename        filename
     ; filename without extension
     ;:short-filename (perun/filename filename)
     :path           tmp-path
     ; parent folder path
     ;:parent-path    (perun/parent-path tmp-path filename)
     :full-path      (.getPath tmpfile)
     ;:extension      (perun/extension filename)
     }))

(defn parse-project-file [file]
  (-> file :full-path io/file slurp edn/read-string))

(defn readme-to-html [project-name fileset]
  (let [readme-path (re-pattern (str project-name "/README.md"))
        md-files (map add-filedata-info (->> fileset boot/user-files (boot/by-re [readme-path])))
        readme  (-> md-files first :full-path io/file slurp)]
    (py/to-html readme)))

(defn process-file [file options fileset]
  (let [[_ project-name version & info] (parse-project-file file)
        info-map (merge file (apply hash-map info))
        readme (readme-to-html project-name fileset)]
    (u/info (prn-str info))
    (assoc info-map :name project-name
                    :filename project-name
                    :content readme)))

(boot/deftask projects
  "Parse projects information"
  [d datafile        DATAFILE        str  "Target datafile with all parsed meta information"
   f create-filename CREATE_FILENAME code "Function that creates final target filename of the file"]
  (boot/with-pre-wrap fileset
      (let [options (merge +defaults+ *opts*)
            projects-files (map add-filedata-info
              (->> fileset boot/user-files (boot/by-name ["project.clj"])))
            parsed-files (map #(process-file % options fileset) projects-files)]
        ;(perun/write-to-file datafile content)
        (u/info "Parsed %s projects files\n" (count parsed-files))
        ;(perun/commit-and-next fileset tmp next-handler)
        (perun/set-meta fileset parsed-files)
        )))
