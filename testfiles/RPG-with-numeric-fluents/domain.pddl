(define (domain RPG)
    (:requirements :strips :typing :negative-preconditions :conditional-effects :equality :universal-preconditions)
    (:types
        location - object
        potion - object
    )
    (:predicates
        (player-at ?l - location)
        (monster-at ?l - location)
        (road ?l1 ?l2 - location)
        (playerhaspotion ?p - potion)
        (potion-at ?p - potion ?l - location)
    )
    (:functions 
    	(player-hp) - number
    	(monster-hp ?l - location) - number
        (monster-attack ?l - location) - number
        (potion-hp ?p - potion) - number
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
    (:action pickup-potion
        :parameters (?l1 - location ?p - potion)
        :precondition (and 
            (potion-at ?p ?l1) 
            (player-at ?l1) 
        )
        :effect (and 
            (not (potion-at ?p ?l1))
            (playerhaspotion ?p)
        )

    )
    (:action consume-potion
        :parameters (?p - potion)
        :precondition (and 
            (playerhaspotion ?p)
        )
        :effect (and 
        	(not (playerhaspotion ?p))
        	(increase (player-hp) (potion-hp ?p))
        )
    )
    (:action fight-monster
        :parameters (?l1 ?l2 - location)
        :precondition (and 
            (road ?l1 ?l2) 
            (player-at ?l1) 
            (monster-at ?l2) 
            (>= (player-hp) (monster-attack ?l2))
        )
        :effect (and 
            (decrease (player-hp) (monster-attack ?l2))
            (when 
                (= (monster-hp ?l2) 1) 
                (not (monster-at ?l2))
            )
            (when
            	(not (= (monster-hp ?l2) 1))
            	(decrease (monster-hp ?l2) 1)
            )
        )
    )
) 
