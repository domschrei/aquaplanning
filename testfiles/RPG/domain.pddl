(define (domain RPG)
    (:requirements :strips :typing :negative-preconditions :conditional-effects :equality :universal-preconditions)
    (:types
        location hp-number - object
    )
    (:constants
        hp1 hp2 hp3 hp4 hp5 - hp-number
    )
    (:predicates
        (player-at ?l - location)
        (monster-at ?l - location)
        (road ?l1 ?l2 - location)
        (player-hp ?hp - hp-number)
        (monster-hp ?l - location ?hp - hp-number)
        (hp-next ?hp1 ?hp2 - hp-number)
    )

    (:action move
        :parameters (?l1 ?l2 - location)
        :precondition (and 
            (road ?l1 ?l2) 
            (player-at ?l1) 
            (not (monster-at ?l2))
        )
        :effect (and 
            (player-at ?l2) 
            (not (player-at ?l1))
        )
    )
    
    (:action fight-monster
        :parameters (?l1 ?l2 - location ?hp1 ?hp2 - hp-number ?hpm1 ?hpm2 - hp-number)
        :precondition (and 
            (road ?l1 ?l2) 
            (player-at ?l1) 
            (monster-at ?l2) 
            (hp-next ?hp1 ?hp2) 
            (player-hp ?hp2) 
            (monster-hp ?l2 ?hpm2)
        )
        :effect (and 
            (not (player-hp ?hp2)) 
            (player-hp ?hp1)
            (when 
                (= ?hpm2 hp1) 
                (not (monster-at ?l2))
            )
            (when 
                (and 
                    (monster-hp ?l2 ?hpm2) 
                    (hp-next ?hpm1 ?hpm2)
                )
                (and 
                    (not (monster-hp ?l2 ?hpm2)) 
                    (monster-hp ?l2 ?hpm1)
                )
            )
        )
    )
) 
