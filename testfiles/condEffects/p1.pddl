(define (problem dp)
  (:domain dp)
  (:objects
  	o4 - object
  )
  (:init
  	(a) (c) (d)
  )
  (:goal (and
  	(not (a))
  	(b)
  	(not (result-a))
  	(result-b)
  	(result-c)
  	(result-d)
  	(not (result-e))
  ))
)