(ns test-simple
  (:use [simple])
  (:use [clojure.test :only [deftest is]]))

(deftest one-test
  (is (= "test" "test")))

(deftest simple-test
  (is (= (simple 1 1) 2))
  (is (= (simple 1 3) 4)))