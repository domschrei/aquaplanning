(define (problem adl1)
  (:domain adl)
  (:requirements :strips :typing :negative-preconditions)
  (:objects
  	t1 t2 t3 t4 t5 t6 - thing
  )
  (:init
  	(is t1)
  	(requires-any t3 t1 t2)
  	(requires-all t4 t1 t3)
  	(requires-none t5 t2 t3)
  	(requires-nand t6 t1 t3)
  	(requires-imply t2 t1 t3)
  )
  (:goal (and
  	(all-turned)
  	(some-turned)
  	(forall (?t - thing) (is ?t))
  ))
)