(defproject joulukalenteri "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2342"]
                 [prismatic/dommy "1.0.0"]
                 [alandipert/storage-atom "1.2.3"]
                 [figwheel "0.1.4-SNAPSHOT"]
                 [com.cemerick/piggieback "0.1.3"]
                 [weasel "0.4.2-SNAPSHOT"]]
  
  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]
  
  :source-paths ["src/clj"]

  :profiles {:dev {:repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :plugins [[lein-figwheel "0.1.4-SNAPSHOT"]]
                   :figwheel {:http-server-root "public"
                              :port 3449}}
             :prod {:cljsbuild {:builds {:client {:source-paths ^:replace ["src/cljs"]
                                                  :compiler {:optimizations :advanced
                                                             :pretty-print false}}}}}}

  :cljsbuild {:builds {:client {:source-paths ["src/cljs" "src/cljs-dev"]
                                :compiler {:output-to "resources/public/joulukalenteri.js"
                                           :output-dir "resources/public/out"
                                           :optimizations :none
                                           :pretty-print true}}}})
