(define (problem RPG-1)
    (:domain RPG)
    (:requirements :strips :typing :negative-preconditions :conditional-effects :equality :universal-preconditions)
    (:objects 
        l1 l2 l3 l4 l5 - location
    )
    (:init
        (player-at l1)
        (road l1 l2)
        (road l2 l3)
        (road l3 l4)
        (road l4 l5)
        (road l1 l5)
        (monster-at l3)
        (monster-at l5)
    )
    (:goal (and
        (player-at l5)
        (killed-at l3)
        (killed-at l5)
        )
    )
)
