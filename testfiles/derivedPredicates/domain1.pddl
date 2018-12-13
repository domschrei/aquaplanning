(define (domain dp)
(:requirements :strips :typing :equality :negative-preconditions)
(:types object)
(:constants
	o1 o2 o3 - object
)
(:predicates 
	(is ?o - object)
)
(:derived (eq-o1-or-o2 ?o - object) (or (= ?o o1) (= ?o o2)))
(:derived (eq-o1-or-o2-or-o3 ?o - object) (or (eq-o1-or-o2 ?o) (= ?o o3)))
(:derived (is-any) (exists (?o) (is ?o)))

(:action turn3
	:parameters (?obj - object) 
	:precondition (and (forall (?o) (not (is ?o))) (not (eq-o1-or-o2 ?obj)))
	:effect (and (is ?obj))
)

(:action turn4
	:parameters (?obj - object) 
	:precondition (and (is-any) (not (eq-o1-or-o2-or-o3 ?obj)))
	:effect (and (is ?obj))
)

)