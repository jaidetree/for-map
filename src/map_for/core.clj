(ns map-for.core
  (:require [clojure.pprint]
            [clojure.string :refer [replace] :rename {replace str-replace}]))

;; Functions to replicate python's os.walk functionality

(def directory? (memfn ^java.io.File isDirectory))
(def list-files (memfn ^java.io.File listFiles))

(defn separate-dirs-from-files
  [files-list]
  (reduce (fn [[dirs files] file]
            (let [name (.getName file)]
              (if (directory? file)
                [(conj dirs name) files]
                [dirs (conj files name)])))
          []
          files-list))

(defn build-file-tree
  [^java.io.File file]
  (cons file
        (separate-dirs-from-files (list-files file))))

(defn file-tree-seq
  [root-dir]
  (->> root-dir
       (clojure.java.io/file)
       (file-seq)
       (filter directory?)
       (map build-file-tree)))

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
