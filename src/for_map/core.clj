(ns for-map.core
  (:require [clojure.pprint]
            [clojure.string :refer [replace]])
  (:refer-clojure :exclude [replace]))

(defn- validate-args
  "Takes the same args as for-map and returns a list of errors. nil errors
  are filtered out"
  [bindings body-map]
  (keep
   identity
   [(when-not (and (vector? bindings)
                   (> (count bindings) 1)
                   (zero? (mod (count bindings) 2)))
      ;; Using str for future compatability with cljs
      (str
       "Invalid 'bindings' must be at least one pair of binding forms. Got '"
       bindings
       "'"))
    (when-not (and (map? body-map)
                   (= 1 (count body-map)))
      (str
       "Invalid 'body-map' must be a hash-map of only 1 key-value pair. Got '"
       body-map
       "'"))]))

(defmacro for-map
 "Hash-map comprehension. Takes a vector of one or more
 binding-form/collection-expr pairs, each followed by zero or more
 modifiers, and a map with a key form and value form and returns a hash-map.
 Collections are iterated in a nested fashion, rightmost fastest,
 and nested coll-exprs can refer to bindings created in prior
 binding-forms. Supported modifiers are: :let [binding-form expr ...],
 :while test, :when test.

 (for-map [x (range 5)]
   {x (* x x)})"
  [bindings body-map]
  (when-first [err-msg (validate-args bindings body-map)]
    (throw (IllegalArgumentException. ^String (str err-msg))))
  (let [[key-form value-form] (first body-map)]
    `(into
       {}
       (for ~bindings
         [~key-form ~value-form]))))

(comment
 (for-map [x (range 10)]
   {x (get "abcdefghijklmnopqrstuvwxyz" x)})
 (for-map [] {})
 (for-map [x] {x 2})
 (for-map [x (range 3)] {x 2 y x})
 (for-map [x ^clojure.lang.Sequential (range 3)] {x 2})
 (for-map [x (range 3)] {x ^String (str "hi")}))
