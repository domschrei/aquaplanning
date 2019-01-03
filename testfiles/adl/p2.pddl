(define (problem adl1)
  (:domain adl)
  (:requirements :strips :typing :negative-preconditions)
  (:objects
  	t1 t2 t3 t4 t5 t6 - thing
  )
  (:init )
  (:goal (and
  	(forall (?t - thing) (is ?t))
  	(or (not (is t1)) (is t2))
  ))
)