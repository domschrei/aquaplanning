(define (domain adl)
(:requirements :strips :typing :equality :negative-preconditions)
(:types thing require-type)
(:constants
	tany tall tnone tnand timply - require-type
)
(:predicates 
	(is ?t - thing)
	(all-turned)
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
)