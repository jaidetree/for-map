(ns for-map.core
  (:require [clojure.pprint]
            [clojure.string :refer [replace]])
  (:refer-clojure :exclude [replace]))

;; Replicates the functionality of the python snippet

(defn relative-name
  [path dir]
  (let [dir-path (str dir)]
    (str-replace dir-path (re-pattern path) "")))

(defn directory-with-no-files?
  [[file dirs files]]
  (empty? files))

(defn pair-file-with-dirs
  [root-path [file dirs]]
  [(relative-name root-path (.getPath file))
   (vec dirs)])

(defn dir->map
  [root-path]
  (->> (file-tree-seq root-path)
       (drop 1)
       (filter directory-with-no-files?)
       (map #(pair-file-with-dirs root-path %))
       (into {})
       (clojure.pprint/pprint)))

(defn -main
  [dir & args]
  (dir->map dir))

(comment
 (dir->map "/Users/jay/Projects/map-for/test/fixtures"))
