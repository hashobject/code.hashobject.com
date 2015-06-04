(set-env!
  :dependencies '[[org.clojure/clojure "1.6.0"]
                  [pygdown "0.1.5"]])

(ns os.hashobject.projects
  {:boot/export-tasks true}
  (:require [boot.core       :as boot]
            [boot.util       :as u]
            [io.perun.core   :as perun]
            [clojure.java.io :as io]
            [clojure.edn     :as edn]
            [pygdown.core    :as py]))


(def ^:private
  +defaults+ {:datafile "meta.edn"})

(defn parse-project-file [file]
  (-> file
      perun/read-file
      edn/read-string))

(defn readme-to-html [project-name fileset]
  (let [readme-path (re-pattern (str project-name "/README.md"))
        readme (->> fileset boot/user-files (boot/by-re [readme-path]) first perun/read-file)]
    (py/to-html readme)))

(defn process-file [file options fileset]
  (let [[_ project-name version & info] (parse-project-file file)
        info-map (apply hash-map info)
        readme (readme-to-html project-name fileset)]
    (u/info (prn-str info))
    (assoc info-map :name project-name
                    :filename project-name
                    :content readme)))

(boot/deftask projects
  "Parse projects information"
  [d datafile        DATAFILE        str  "Target datafile with all parsed meta information"
   f create-filename CREATE_FILENAME code "Function that creates final target filename of the file"]
  (let [tmp (boot/temp-dir!)]
    (fn middleware [next-handler]
      (fn handler [fileset]
        (let [options (merge +defaults+ *opts*)
              projects-files (->> fileset boot/user-files (boot/by-name ["project.clj"]))
              parsed-files (map #(process-file % options fileset) projects-files)
              datafile (io/file tmp (:datafile options))
              content (prn-str parsed-files)]
          (perun/write-to-file datafile content)
          (u/info "Parsed %s projects files\n" (count projects-files))
          (perun/commit-and-next fileset tmp next-handler))))))
