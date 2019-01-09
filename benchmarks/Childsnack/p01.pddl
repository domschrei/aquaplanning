; python2 child-snack-generator.py pool 2257 1 3 0.4 1.3
; child-snack task with 1 children and 0.4 gluten factor 
; constant factor of 1.3
; random seed: 2257

(define (problem prob-snack)
  (:domain child-snack)
  (:objects
    child1 - child
    bread1 - bread-portion
    content1 - content-portion
    tray1 tray2 tray3 - tray
    table1 table2 table3 - place
    sandw1 sandw2 - sandwich
  )
  (:init
     (at tray1 kitchen)
     (at tray2 kitchen)
     (at tray3 kitchen)
     (at_kitchen_bread bread1)
     (at_kitchen_content content1)
     (not_allergic_gluten child1)
     (waiting child1 table1)
     (notexist sandw1)
     (notexist sandw2)
  )
  (:goal
    (and
     (served child1)
    )
  )
)
