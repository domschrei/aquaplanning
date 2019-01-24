(define (domain equality)
(:requirements :strips :typing :equality :negative-preconditions)
(:types object )
(:constants o1 - object )
(:predicates 
	(is ?o - object)
	(finished)
)
(:action turn-o1
	:parameters (?o - object) 
	:precondition (and (not (is ?o)) (= ?o o1))
	:effect (and
		(is ?o)
	)
)

(:action turn-other-than-o1
	:parameters (?o - object)
	:precondition (and (not (is ?o)) (not (= ?o o1)))
	:effect (and
		(is ?o)
	)
)

(:action finish
	:parameters (?o1 ?o2 ?o3 - object)
	:precondition (and (= ?o1 ?o2) (= ?o2 ?o3) (forall (?o - object) (is ?o)))
	:effect (and
		(finished)
	)
)
)