(define (problem TM-1)
(:domain TM)
(:objects
    p1 p2 p3 p4 p5 p6 pb - position
    s0 s1 s2 - state
    zero one blank - symbol
)
(:init
    ; -----------------------------------
    ; Tape layout
    (next-position p1 right p2)
    (next-position p2 right p3)
    (next-position p3 right p4)
    (next-position p4 right p5)
    (next-position p5 right p6)
    (next-position p6 right pb)
    
    (next-position pb left p6)
    (next-position p6 left p5)
    (next-position p5 left p4)
    (next-position p4 left p3)
    (next-position p3 left p2)
    (next-position p2 left p1)
    
    (next-position p1 stop p1)
    (next-position p2 stop p2)
    (next-position p3 stop p3)
    (next-position p4 stop p4)
    (next-position p5 stop p5)
    (next-position p6 stop p6)
    (next-position pb stop pb)
    ; -----------------------------------
    
    ; Start configuration
    (reader-at p1)
    (state s0)
    (symbol-at p1 zero)
    (symbol-at p2 zero)
    (symbol-at p3 zero)
    (symbol-at p4 one)
    (symbol-at p5 zero)
    (symbol-at p6 zero)
    (symbol-at pb blank)
    
    ; Transition table:
    ; Go to the right reading zeros
    ; When reading a one, overwrite the rest
    ;   of the tape with ones
    ; Stop at the right end of the tape
    ;         symbol state | symbol state direction
    (program  zero   s0      zero   s0    right    )
    (program  zero   s1      one    s1    right    )
    (program  zero   s2      zero   s2    stop     )
    (program  one    s0      one    s1    right    )
    (program  one    s1      one    s1    right    )
    (program  blank  s0      blank  s2    left     )
    (program  blank  s1      blank  s2    left     )
    (program  zero   s2      zero   s2    stop     )
    (program  one    s2      one    s2    stop     )
)
(:goal
    (and
        (halted)
    )
)
)


