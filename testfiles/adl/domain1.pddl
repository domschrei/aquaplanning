(define (domain adl)
(:requirements :strips :typing :equality :negative-preconditions)
(:types thing require-type)
(:constants
	tany tall tnone tnand timply - require-type
)
(:predicates 
	(is ?t - thing)
	(requires-any ?t ?t1 ?t2 - thing)
	(requires-all ?t ?t1 ?t2 - thing)
	(requires-none ?t ?t1 ?t2 - thing)
	(requires-nand ?t ?t1 ?t2 - thing)
	(requires-imply ?t ?t1 ?t2 - thing)
	(all-turned)
	(some-turned)
	(unturned ?t - thing)
)
(:action turn
	:parameters (?t ?t1 ?t2 - thing ?r - require-type) 
	:precondition (and )
	:effect (and
		(when 
			(and
				(= ?r tany)
				(requires-any ?t ?t1 ?t2)
				(or
					(not (not (not (not (is ?t1)))))
					(is ?t2)
				)
			)
			; THEN
			(and
				(is ?t)
			)
		)
		(when 
			(and
				(= ?r tall)
				(requires-all ?t ?t1 ?t2)
				(and
					(is ?t1)
					(is ?t2)
				)
			)
			; THEN
			(and
				(is ?t)
			)
		)
		(when 
			(and
				(= ?r tnone)
				(requires-none ?t ?t1 ?t2)
				(not (not (not (or
					(not (not (is ?t1)))
					(is ?t2)
				))))
			)
			; THEN
			(and
				(is ?t)
			)
		)
		(when 
			(and
				(= ?r tnand)
				(requires-nand ?t ?t1 ?t2)
				(not (and
					(is ?t1)
					(is ?t2)
				))
			)
			; THEN
			(and
				(is ?t)
			)
		)
		(when 
			(and
				(not (not (= ?r timply)))
				(requires-imply ?t ?t1 ?t2)
				(imply
					(is ?t1)
					(is ?t2)
				)
			)
			; THEN
			(and
				(is ?t)
			)
		)
	)
)

(:action turn-all
	:parameters ()
	:precondition (and )
	:effect (and
		(when
			(forall (?t - thing) (and
				(is ?t)
			))
			; THEN
			(all-turned)
		)
	)
)

(:action turn-some
	:parameters (?t - thing)
	:precondition (and )
	:effect (and
		(when
			(or
				(is ?t)
				(exists (?th - thing) (and
					(is ?th)
				))
			)
			; THEN
			(some-turned)
		)
	)
)
)



)