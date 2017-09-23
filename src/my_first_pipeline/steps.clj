(ns my-first-pipeline.steps
  (:require [lambdacd.steps.shell :as shell]
            [lambdacd-git.core :as lambdacd-git]
            [clojure.tools.logging :as log]))

(def repo-uri "https://github.com/conusio/conus.git")
(def repo-branch "master")

(defn wait-for-repo [args ctx]
  (lambdacd-git/wait-for-git ctx repo-uri :ref (str "refs/heads/" repo-branch)))


(defn clone [args ctx]
  (let [revision (:revision args)
        cwd      (:cwd args)
        ref      (or revision repo-branch)]
    (lambdacd-git/clone ctx repo-uri ref cwd)))

(defn some-step-that-does-nothing [args ctx]
  {:status :success})

(defn my-machine-deploy [args ctx]
  (log/info "args:" args "ctx:" ctx)
  (shell/bash ctx (:cwd args) "lein run"))

(defn build [args ctx]
  (log/info "args:" args "ctx:" ctx)
  (shell/bash ctx (:cwd args) "lein uberjar"
                              "cp target/uberjar/conus.jar /home/conus/conus"))

;; sudo -E java -cp target/uberjar/conus.jar:resources conus.core &
(defn deploy [args ctx]
  (log/info "args:" args "ctx:" ctx)
  (shell/bash ctx (:cwd args) "kill $(pidof java)"
                              "cd /home/conus/conus"
                              "java -cp conus.jar:resources conus.core"))
