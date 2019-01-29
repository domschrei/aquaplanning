(define (domain dp)
(:requirements :strips :typing :equality :negative-preconditions)
(:types object)
(:constants
	o1 o2 o3 - object
)
(:predicates 
	(a)
	(b)
)

(:action do
	:parameters () 
	:precondition (and )
	:effect (and 
		(when (a)
			(b)
		)
		(when (b)
			(not (a))
		)
	)
)
)