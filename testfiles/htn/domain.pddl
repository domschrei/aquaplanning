(define (domain easy-car)
    (:requirements :strips :typing :negative-preconditions :htn :equality)
    (:types car place - object
    )
    (:predicates (at-place ?c - car ?p - place) (road ?p1 ?p2 - place)
    )
		
    (:action move
            :parameters (?c - car ?p1 - place ?p2 - place)
            :precondition (and (at-place ?c ?p1) (not (= ?p1 ?p2)))
            :effect (and (not (at-place ?c ?p1)) (at-place ?c ?p2)))
    
    (:method do_move
        :parameters (?c - car ?p - place)
        :expansion  (
                        (tag t1 (move ?c ?pbefore ?p))
                    )
        :constraints( 
                    and (before (and 
                        (at-place ?c ?pbefore) 
                        (road ?pbefore ?p)
                    ) t1))
    )
    
    (:method do_move
        :parameters (?c - car ?p - place)
        :expansion  (
                        (tag t1 (move ?c ?pbefore ?pbetween))
                        (tag t2 (move ?c ?pbetween ?p))
                    )
        :constraints( 
                    and (before (and 
                        (at-place ?c ?pbefore) 
                        (not (road ?pbefore ?p)) 
                        (road ?pbefore ?pbetween) 
                        (road ?pbetween ?p)
                    ) t1))
    )
)
