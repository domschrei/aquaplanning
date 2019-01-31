(define (domain dp)
(:requirements :strips :typing :equality :negative-preconditions)
(:types object)
(:constants
	o1 o2 o3 - object
)
(:predicates 
	(a)	(b) (c) (d) (e)
	(result-a) (result-b) (result-c) (result-d) (result-e)
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

(:action do2
	:parameters ()
	:precondition (and )
	:effect (and
		(when (a) (result-a))
		(when (b) (result-b))
		(when (c) (result-c))
		(when (d) (result-d))
		(when (e) (result-e))
	)
)
)