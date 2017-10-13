(ns hello-clojureweb.core
  (:require [ring.adapter.jetty :as jetty]
            [clojure.pprint :as pp]
            [bidi.bidi :as bidi]
            [bidi.ring :refer (make-handler)]
            [hiccup.core :refer (html)]))

(def routes ["/" {"foobar" :foo
                  "barfoo" :bar
                  "article.html" :render-fancy-stuff}])

(defn foo-handler
  [request]
  {:status 200
   :body "Foobar 42!"})

(defn bar-handler
  [request]
  {:status 200
   :body "Barfoo 42!"})

(defn render-fancy-stuff
  [_]
  {:status 200
   :headers {"content-type" "text/html"}
   :body
   (let [foobar 42]
     (html [:span
            [:h1 (str "Hello hiccup world!" foobar)]
            [:p {:style "color: red"}
             "I'm still part of the span"]]))})

(defn nil-handler
  [{:keys [uri]}]
  {:status 404
   :body (str "sorry, dunno how to process " uri)})

(def handlers {:foo foo-handler
               :bar bar-handler
               :render-fancy-stuff render-fancy-stuff
               nil  nil-handler})

(def my-handler
  (make-handler routes handlers))
