(define (problem q-test-1)
(:domain q-test)
(:requirements :strips :typing)
(:objects
    o1 o2 o3 o4 o5 - object2
)
(:init
    (set o1)
    (set o2)
    (set o3)
    (set o4)
    (set o5)
)
(:goal (and
    (not (set o1))
))
)
