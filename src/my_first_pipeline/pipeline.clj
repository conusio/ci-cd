(ns my-first-pipeline.pipeline
  (:use [lambdacd.steps.control-flow]
        [my-first-pipeline.steps])
  (:require
        [lambdacd.steps.manualtrigger :as manualtrigger]))

(def pipeline-def
  `(
    manualtrigger/wait-for-manual-trigger
    (with-workspace
      clone
      build
      deploy)))
