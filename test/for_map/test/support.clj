(ns for-map.test.support
  (:require [clojure.test :refer [assert-expr do-report]])
  (:import (clojure.lang Compiler$CompilerException)))

(defmethod assert-expr 'thrown-in-macro? [msg form]
  ;; (is (thrown-in-macro? c expr))
  ;; Asserts that evaluating expr throws an exception of class c in the
  ;; macro-expansion phase.
  (let [expected-class (second form)
        body (nthnext form 2)]
    `(try (eval ~@body)
          (do-report {:type :fail
                      :message ~msg
                      :expected '~form
                      :actual nil})
          (catch Compiler$CompilerException e#
            (let [actual-class# (-> e# Throwable->map :via last :type resolve)]
              (if (identical? ~expected-class actual-class#)
                (do-report {:type :pass
                            :message ~msg
                            :expected ~expected-class
                            :actual actual-class#})
                (do-report {:type :fail
                            :message ~msg
                            :expected ~expected-class
                            :actual actual-class#})))
            e#))))
