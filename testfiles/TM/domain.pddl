; A Turing Machine in PDDL
(define (domain TM)
  (:requirements :strips :typing :negative-preconditions)
  (:types 
        position state symbol direction - object
  )
  (:constants
        left right stop - direction
  )
  (:predicates
        (next-position ?p - position ?d - direction ?p2 - position)
        (reader-at ?p - position)
        (symbol-at ?p - position ?x - symbol)
        (state ?s - state)
        (program ?x - symbol ?s - state ?x2 - symbol ?s2 - state ?d - direction)
        (halted)
  )
  
  (:action transition
        :parameters (?p ?p2 - position ?s ?s2 - state 
                     ?x ?x2 - symbol ?d - direction)
        :precondition (and
            (reader-at ?p)
            (symbol-at ?p ?x)
            (state ?s)
            (program ?x ?s ?x2 ?s2 ?d)
            (next-position ?p ?d ?p2)
        )
        :effect (and
            (when 
                (not (= ?p ?p2))
                (and (not (reader-at ?p)) (reader-at ?p2))
            )
            (when 
                (not (= ?s ?s2))
                (and (not (state ?s)) (state ?s2))
            )
            (when 
                (not (= ?x ?x2))
                (and (not (symbol-at ?p ?x)) (symbol-at ?p ?x2))
            )
        )
  )
  
  (:action halt
        :parameters (?p - position ?s - state ?x - symbol)
        :precondition (and
            (reader-at ?p)
            (symbol-at ?p ?x)
            (state ?s)
            (program ?x ?s ?x ?s stop)
        )
        :effect (and
            (halted)
        )
  )
)
