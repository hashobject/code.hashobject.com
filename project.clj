(defproject os.hashobject.com "0.1.0"
  :description "Hashobject team open source corner."
  :url "http://os.hashobject.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [hiccup "1.0.4"]
                 [pygdown "0.1.5"]
                 [sitemap "0.2.1"]
                 [leiningen-core "2.3.4"]]
  :plugins [[lein-shell "0.1.0"]
            [lein-sitemap "0.1.0"]]
  :sitemaps ["http://os.hashobject.com/sitemap.xml"]
  :aliases {"index-html" ["trampoline" "run" "-m" "os.hashobject.generator/generate-index"]
            "projects-html" ["trampoline" "run" "-m" "os.hashobject.generator/generate-projects"]
            "pygments-css" ["shell" "pygmentize -S default -f html > /frontend/styl/pygments.css"]
            "sitemap-xml" ["trampoline" "run" "-m" "os.hashobject.generator/generate-sitemap"]
            "site-build" ["shell" "./frontend/node_modules/grunt-cli/bin/grunt" "--gruntfile" "frontend/Gruntfile.js" "build"]
            "site-deploy" ["shell" "./frontend/node_modules/grunt-cli/bin/grunt" "--gruntfile" "frontend/Gruntfile.js" "deploy"]})

