(ns paxos.test-acceptor
  (:use [paxos.acceptor])
  (:use [clojure.test :only [deftest is]]))

(deftest prepare-accept
  (reset-acceptor)
  (is (= (prepare-request 1) '(1 nil)))
  (is (= (accept-request 1 "one") '(1 "one")))
  (is (= (prepare-request 2) '(2 "one"))))

(deftest prepare-two-accept
  (reset-acceptor)
  (is (= (prepare-request 1) '(1 nil)))
  (is (= (prepare-request 2) '(2 nil)))
  (is (= (accept-request 1 "one") '(2 nil)))
  (is (= (accept-request 2 "two") '(2 "two")))
  (is (= (prepare-request 3) '(3 "two"))))
