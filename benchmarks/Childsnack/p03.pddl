; python2 child-snack-generator.py pool 21707 3 3 0.4 1.3
; child-snack task with 3 children and 0.4 gluten factor 
; constant factor of 1.3
; random seed: 21707

(define (problem prob-snack)
  (:domain child-snack)
  (:objects
    child1 child2 child3 - child
    bread1 bread2 bread3 - bread-portion
    content1 content2 content3 - content-portion
    tray1 tray2 tray3 - tray
    table1 table2 table3 - place
    sandw1 sandw2 sandw3 sandw4 - sandwich
  )
  (:init
     (at tray1 kitchen)
     (at tray2 kitchen)
     (at tray3 kitchen)
     (at_kitchen_bread bread1)
     (at_kitchen_bread bread2)
     (at_kitchen_bread bread3)
     (at_kitchen_content content1)
     (at_kitchen_content content2)
     (at_kitchen_content content3)
     (no_gluten_bread bread2)
     (no_gluten_content content2)
     (allergic_gluten child2)
     (not_allergic_gluten child1)
     (not_allergic_gluten child3)
     (waiting child1 table2)
     (waiting child2 table2)
     (waiting child3 table3)
     (notexist sandw1)
     (notexist sandw2)
     (notexist sandw3)
     (notexist sandw4)
  )
  (:goal
    (and
     (served child1)
     (served child2)
     (served child3)
    )
  )
)
