(define (problem dp)
  (:domain dp)
  (:objects
  	o4 - object
  )
  (:init
  )
  (:goal (and
  	(is o3)
  	(is o4)
  	(eq-o1-or-o2-or-o3 o1)
  	(not (eq-o1-or-o2-or-o3 o4))
  ))
)