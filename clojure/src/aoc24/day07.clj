(ns aoc24.day07
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[target & nums] (map #(parse-double %) (str/split line #"[: ]+"))]
    {:target target :nums nums}))

(defn apply-op [op a b]
  (case op
    :+ (+ a b)
    :* (* a b)))

(defn calculate [nums ops]
  (reduce (fn [acc [num op]]
            (apply-op op acc num))
          (first nums)
          (map vector (rest nums) ops)))

(defn solve-equation [equation]
  (let [target (:target equation)
        nums (:nums equation)
        num-ops (dec (count nums))]
    (some (fn [ops]
            (= target (calculate nums ops)))
          (for [i (range (int (Math/pow 2 num-ops)))
                :let [ops (map #(if (bit-test i %) :* :+) (range num-ops))]]
            ops))))

(defn part-1 [input]
  (let [equations (map parse-line input)]
    (->> equations
         (filter solve-equation)
         (map :target)
         (reduce +)
         long)))

(defn part-2 [input]
  " -- AI! write code the solve part-2!!!
  The engineers seem concerned; the total calibration result you gave them is nowhere close to being within safety tolerances. Just then, you spot your mistake: some well-hidden elephants are holding a third type of operator.

The concatenation operator (||) combines the digits from its left and right inputs into a single number. For example, 12 || 345 would become 12345. All operators are still evaluated left-to-right.

Now, apart from the three equations that could be made true using only addition and multiplication, the above example has three more equations that can be made true by inserting operators:

156: 15 6 can be made true through a single concatenation: 15 || 6 = 156.
7290: 6 8 6 15 can be made true using 6 * 8 || 6 * 15.
192: 17 8 14 can be made true using 17 || 8 + 14.
Adding up all six test values (the three that could be made before using only + and * plus the new three that can now be made by also using ||) produces the new total calibration result of 11387.

Using your new knowledge of elephant hiding spots, determine which equations could possibly be true. What is their total calibration result? 
   
   ")

(def example (str/split-lines "190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20"))

(println (part-1 example))

(def input (str/split-lines (slurp "resources/aoc24/day07.txt")))

(println (part-1 input))