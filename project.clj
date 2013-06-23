(defproject blog.hashobject.com "0.1.0"
  :description "HashObject team open source corner."
  :url "http://blog.hashobject.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [hiccup "1.0.3"]
                 [markdown-clj "0.9.26"]
                 [endophile "0.1.0"]
                 [sitemap "0.2.0"]]
  :plugins [[lein-shell "0.1.0"]
            [lein-sitemap "0.1.0"]]
  :sitemaps ["http://os.hashobject.com/sitemap.xml"]
  :aliases {"index-html" ["trampoline" "run" "-m" "os.hashobject.generator/generate-index"]
            "posts-html" ["trampoline" "run" "-m" "os.hashobject.generator/generate-projects"]
            "sitemap-xml" ["trampoline" "run" "-m" "os.hashobject.generator/generate-sitemap"]
            "site-build" ["shell" "./frontend/node_modules/grunt-cli/bin/grunt" "--gruntfile" "frontend/Gruntfile.js" "build"]
            "site-deploy" ["shell" "./frontend/node_modules/grunt-cli/bin/grunt" "--gruntfile" "frontend/Gruntfile.js" "deploy"]})

