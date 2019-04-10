(define (domain RPG)
    (:requirements :strips :typing :negative-preconditions :conditional-effects :equality :universal-preconditions)
    (:types
        location - object
    )
    (:predicates
        (player-at ?l - location)
        (monster-at ?l - location)
        (killed-at ?l - location)
        (road ?l1 ?l2 - location)
    )

    (:action move
        :parameters (?l1 ?l2 - location)
        :precondition (and 
            (road ?l1 ?l2) 
            (player-at ?l1) 
        )
        :effect (and 
            (player-at ?l2) 
            (not (player-at ?l1))
        )
    )
    
    (:action fight-monster
        :parameters (?l1 ?l2 - location)
        :precondition (and 
            (road ?l1 ?l2) 
            (player-at ?l1) 
            (monster-at ?l2) 
        )
        :effect (and
            (not (monster-at ?l2))
            (killed-at ?l2)
        )
    )
) 
