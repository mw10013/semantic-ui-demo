(ns semantic-ui-demo.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [semantic-ui-demo.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
