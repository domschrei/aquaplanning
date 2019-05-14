(define (problem prob)
(:domain easy-car)
(:requirements :strips :typing :negative-preconditions :htn :equality)

(:objects 
    c - car
    p1 p2 p3 - place
)

(:init 
    (at-place c p1)
    (road p1 p2)
    (road p2 p3)
)
(:goal
    :tasks  (
                (tag t1 (do_move c p3))
            )

    :constraints(and
                    (after (and
                                
                            ) t1)
                )
)
)
