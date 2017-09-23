(ns conusci.pipeline
  (:use [lambdacd.steps.control-flow]
        [conusci.steps])
  (:require
        [lambdacd.steps.manualtrigger :as manualtrigger]))

(def pipeline-def
  `(
    manualtrigger/wait-for-manual-trigger
    (with-workspace
      clone
      build
      deploy)))
