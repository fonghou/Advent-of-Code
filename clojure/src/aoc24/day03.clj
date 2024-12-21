(ns aoc24.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-input [resource]
  (str/trim (slurp (io/resource resource))))

(defn- calculate-product [[_ x y]]
  (* (Integer/parseInt x) (Integer/parseInt y)))

(defn part-1
  ([input]
   (let [mul-regex #"mul\((\d{1,3}),(\d{1,3})\)"
         matches (re-seq mul-regex input)]
     (reduce + (map calculate-product matches)))))

(defn part-2
  ([input]
   (let [mul-regex #"mul\((\d{1,3}),(\d{1,3})\)"
         do-regex #"do\(\)"
         dont-regex #"don't\(\)"
         enabled? (atom true)
         sum (atom 0)]
     (loop [s input]
       (cond
         (str/starts-with? s "mul") (let [match (re-find mul-regex s)]
                                      (when @enabled?
                                        (swap! sum + (calculate-product match)))
                                      (recur (subs s (count match))))
         (str/starts-with? s "do") (do
                                     (reset! enabled? true)
                                     (recur (subs s (count (re-find do-regex s)))))
         (str/starts-with? s "don't") (do
                                        (reset! enabled? false)
                                        (recur (subs s (count (re-find dont-regex s)))))
         :else (recur (subs s 1))))
     @sum)))

(defn -main []
  (let [input (parse-input "aoc24/day03.txt")]
    (println "Part 1:" (part-1 input))
    (println "Part 2:" (part-2 input))))
