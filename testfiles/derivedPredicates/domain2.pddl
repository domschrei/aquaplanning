(define (domain dp)
(:requirements :strips :typing :equality :negative-preconditions)
(:types 
	object1 object2 - object
)
(:constants o0 - object2)
(:predicates 
	(next-to ?o1 ?o2 - object)
	(validated)
)
; predicate for transitive reachability
(:derived (reachable ?o1 ?o2 - object) 
	(or 
		; base case: each object is reachable from itself
		(= ?o1 ?o2)
		; direct connection between objects
		(next-to ?o1 ?o2) 
		; transitive case with an intermediate object
		(exists (?o3 - object) (and 
			(next-to ?o1 ?o3) (reachable ?o3 ?o2)
		))
		(exists (?o3 - object) (and 
			(reachable ?o1 ?o3) (next-to ?o3 ?o2)
		))
	)
)

(:action validate
	:parameters () 
	:precondition (and 
		(forall (?x ?y - object1)
			(reachable ?x ?y)
		)
		(forall (?x - object1) 
			(not (reachable ?x o0))
		)
	)
	:effect (and (validated))
)
)