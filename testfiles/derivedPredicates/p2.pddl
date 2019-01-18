(define (problem dp)
  (:domain dp)
  (:objects
	o1 o2 o3 o4 o5 o6 o7 - object1
  )
  (:init
  	(next-to o1 o2)
  	(next-to o2 o3)
  	(next-to o3 o4)
  	(next-to o4 o5)
  	(next-to o5 o6)
  	(next-to o6 o7)
  	(next-to o7 o1)
  )
  (:goal (and
  	(validated)
  ))
)