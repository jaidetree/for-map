(ns for-map.test.core
  (:require [for-map.core :refer :all]
            [clojure.test :refer :all]
            [for-map.test.support]))

(deftest for-map-test
  (testing "for-map returns a hash-map with no modifiers"
    (is (= (for-map [x (range 5)]
            {x (* x x)})
           {0 0
            1 1
            2 4
            3 9
            4 16}))))

(deftest for-map-let-test
  (testing "for-map supports :let modifier"
    (is (= (for-map [x (range 5)
                     :let [y (* x x)]]
            {x y})
           {0 0
            1 1
            2 4
            3 9
            4 16}))))

(deftest for-map-when-test
  (testing "for-map supports :when modifer"
    (is (= (for-map [x (range 5)
                     :when (odd? x)]
            {x (* x x)})
           {1 1
            3 9}))))

(deftest for-map-while-test
  (testing "for-map supports :while modifer"
    (is (= (for-map [x (range 5)
                     :while (< x 4)]
            {x (* x x)})
           {0 0
            1 1
            2 4
            3 9}))))

(deftest for-map-invalid-bindings-test
  (testing "for-map throws error with invalid bindings"
    (is (thrown-in-macro? IllegalArgumentException `(for-map [] {})))
    (is (thrown-in-macro? IllegalArgumentException `(for-map [x] {x x})))))

(deftest for-map-invalid-body-map-test
  (testing "for-map throws error with invalid body-map"
    (is (thrown-in-macro? IllegalArgumentException `(for-map [x (range 3)]
                                                      {})))
    (is (thrown-in-macro? IllegalArgumentException `(for-map [x (range 3)]
                                                      {x x :a 2})))))

(comment
 (run-tests))
