(define (problem q-test-1)
(:domain q-test)
(:requirements :strips :typing)
(:objects
    o1 o2 o3 o4 o5 - object1
)
(:init
    
)
(:goal (and
    (forall (?o - object1) (marked ?o))
))
)
