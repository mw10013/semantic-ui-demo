(ns semantic-ui-demo.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [semantic-ui-demo.core-test]))

(doo-tests 'semantic-ui-demo.core-test)
