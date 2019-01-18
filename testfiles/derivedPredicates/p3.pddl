(define (problem dp)
  (:domain dp)
  (:objects )
  (:init
  	(next-to t1 t2)
  	(next-to t2 t3)
  	(next-to t3 t4)
  	(next-to t4 t5)
  )
  (:goal (and
  	(validated)
  ))
)