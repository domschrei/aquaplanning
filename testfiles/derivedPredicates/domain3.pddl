(define (domain dp)
(:requirements :strips :typing :equality :negative-preconditions)
(:types 
	t1 t2 t3 t4 t5 - tile
)
(:constants )
(:predicates 
	(next-to ?t1 ?t2 - tile)
	(validated)
)
(:derived (same-color ?t1 ?t2 - tile) 
	(and
		;(not (next-to ?t1 ?t2))
		;(not (next-to ?t2 ?t1))
		(or 
			(exists (?t3 - tile) (and 
				(next-to ?t2 ?t3) 
				(not (same-color ?t3 ?t1))
			))
			(exists (?t3 - tile) (and 
				(next-to ?t1 ?t3) 
				(not (same-color ?t3 ?t2))
			))
			(= ?t1 ?t2)
		)
	)
)

(:action validate
	:parameters () 
	:precondition (and 

		(same-color t1 t1)
		(not (same-color t1 t2))
		(same-color t1 t3)
		(not (same-color t1 t4))
		(same-color t1 t5)
		
		(same-color t2 t2)
		(not (same-color t2 t3))
		(same-color t2 t4)
		(not (same-color t2 t5))

		(same-color t3 t3)
		(not (same-color t3 t4))
		(same-color t3 t5)
		
		(same-color t4 t4)
		(not (same-color t4 t5))
		
		(same-color t5 t5)
	)
	:effect (and (validated))
)
)