(ns for-map.core
  (:require [clojure.pprint]
            [clojure.string :refer [replace]])
  (:refer-clojure :exclude [replace]))

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
  [seq-expers body-map]
  (let [[key-form value-form] (first body-map)]
    `(into
       {}
       (for ~seq-expers
         [~key-form ~value-form]))))

(comment
 (for-map [x (range 10)]
   {x (get "abcdefghijklmnopqrstuvwxyz" x)}))
