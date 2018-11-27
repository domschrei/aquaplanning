(define (domain q-test)

(:requirements :strips :typing)
(:types
    object1 object2
)
(:constants )
(:predicates
    (marked ?o - object1)
    (set ?o - object2)
)

(:action mark
    :parameters (?o - object1)
    :precondition (and
        (forall (?o2 - object1) (not (marked ?o2)))
    )
    :effect (and
        (forall (?o2 - object1)
            (when (not (marked ?o2))
                (marked ?o2)
            )
        )
    )
)

(:action unset
    :parameters (?o - object2)
    :precondition (and )
    :effect (and
        (when (forall (?o2 - object2) (set ?o2))
            (not (set ?o)))
    )
)
)
