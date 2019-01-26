(define (problem gripper-x-1 )
(:domain gripper-typed )
	;(:requirements :strips :typing :negative-preconditions :htn :equality)

(:objects rooma roomb - room
             ball2 ball1 - ball )
(:init (at-robby rooma )
          (free left )
          (free right )
          (at ball2 rooma )
          (at ball1 rooma ) )
(:goal
	(and
        (at ball2 roomb )
        (at ball1 roomb )
    )
))
