(define (domain equality)
(:requirements :strips :typing :equality :negative-preconditions)
(:types object )
(:constants )
(:predicates 
	(is ?o - object)
	(finished)
)
(:action validate
	:parameters (?o1 ?o2 ?o3 ?o4 ?o5 ?o6 ?o7 ?o8 ?o9 ?o10 - object)
	:precondition (and (= ?o1 ?o2) (= ?o2 ?o3) (= ?o3 ?o4) (= ?o4 ?o5) (= ?o5 ?o6) (= ?o6 ?o7) (= ?o7 ?o8) (= ?o8 ?o9) (= ?o9 ?o10))
	:effect (and
		(finished)
	)
)
)