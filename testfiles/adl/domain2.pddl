(define (domain adl)
(:requirements :strips :typing :equality :negative-preconditions)
(:types thing require-type)
(:constants
	tany tall tnone tnand timply - require-type
)
(:predicates 
	(is ?t - thing)
	(all-turned)
	(pred-a)
	(pred-b)
	(pred-c)
	(pred-d)
)

(:action turn-all
	:parameters ()
	:precondition (or 
		(all-turned)
		(forall (?t - thing) (not (is ?t)))
	)
	:effect (and
		(forall (?t - thing) (is ?t))
	)
)



(:action test-de-morgan
	:parameters (?t1 ?t2 - thing)
	:precondition (and (not (and (is ?t1) (is ?t2))))
	:effect (and )
)

(:action test-nested-conditions
	:parameters (?t1 ?t2 - thing)
	:precondition (or (or (or (is ?t1) (is ?t2)) (is ?t1)) (is ?t2))
	:effect (and (is ?t1) (is ?t2) (is ?t1))
)
(:action test-nested-conditions-2
	:parameters (?t1 ?t2 - thing)
	:precondition (not (or (or (or (is ?t1) (is ?t2)) (is ?t1)) (is ?t2)))
	:effect (and (is ?t1) (is ?t2) (is ?t1))
)

(:action test-dnf 
	:parameters ( )
	:precondition 
		(and
			(and 
				(or 
					(pred-a) 
					(pred-b)
				) 
				(and 
					(or (pred-c) (not (pred-a))) 
					(pred-d)
				)
			)
		)
	:effect (and )
)
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
)