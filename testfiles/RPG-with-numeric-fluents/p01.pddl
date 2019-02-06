(define (problem RPG-1)
    (:domain RPG)
    (:requirements :strips :typing :negative-preconditions :conditional-effects :equality :universal-preconditions)
    (:objects 
        l1 l2 l3 l4 l5 - location
        p1 - potion
    )
    (:init
    	(potion-at p1 l1)
        (= (potion-hp p1) 2)
    	(= (player-hp) 4)
        (player-at l1)
        (road l1 l2)
        (road l2 l3)
        (road l3 l4)
        (road l4 l5)
        (monster-at l3)
        (= (monster-hp l3) 2)
        (= (monster-attack l3) 2)
        (monster-at l5)
        (= (monster-hp l5) 1)
        (= (monster-attack l5) 1)
    )
    (:goal
        (player-at l5)
    )
)
