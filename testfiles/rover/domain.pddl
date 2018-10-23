(define (domain ROVER)
	(:requirements :strips :typing)
	(:types rover waypoint store camera objective mode lander - object)
	(:constants )
	(:predicates	(available ?x - rover)
  					(at ?x - rover ?p - waypoint)
  					(visible ?p1 - waypoint ?p2 - waypoint)
  					(can_traverse ?x - rover ?p1 - waypoint ?p2 - waypoint)
  					(store_of ?s - store ?x - rover)
  					(empty ?s - store)
  					(full ?s - store)
  					(equipped_for_soil_analysis ?x - rover)
  					(at_soil_sample ?p - waypoint)
  					(have_soil_analysis ?x - rover ?p - waypoint)
  					(equipped_for_rock_analysis ?x - rover)
  					(at_rock_sample ?p - waypoint)
  					(have_rock_analysis ?x - rover ?p - waypoint)
  					(equipped_for_imaging ?x - rover) 
  					(calibration_target ?c - camera ?o - objective)
  					(visible_from ?o - objective ?p - waypoint) 
  					(on_board ?c - camera ?x - rover)
  					(calibrated ?c - camera ?x - rover)
  					(supports ?c - camera ?m - mode)
  					(have_image ?x - rover ?o - objective ?m - mode)
  					(at_lander ?l - lander ?p - waypoint)
  					(channel_free ?l - lander)
  					(communicated_soil_data ?p - waypoint) 
  					(communicated_rock_data ?p - waypoint) 
  					(communicated_image_data ?o - objective ?m - mode) 
  					(visited ?p - waypoint)
  	)


;;; Operators
;;;;;;;;;;;;;;;;;

	(:action navigate
	     :parameters (?x - rover ?p1 - waypoint ?p2 - waypoint)
	     :precondition (and (available ?x) (at ?x ?p1) (can_traverse ?x ?p1 ?p2) (visible ?p1 ?p2))
	     :effect (and
	     			(not(at ?x ?p1)) (at ?x ?p2)
	     		)
	)
          
	(:action sample_soil
	     :parameters (?x - rover ?s - store ?p - waypoint)
	     :precondition (and
	     					(at ?x ?p) (at_soil_sample ?p) (equipped_for_soil_analysis ?x) 
	     					(store_of ?s ?x) (empty ?s)
	     				)
	     :effect (and 
		   			(not(empty ?s)) (not(at_soil_sample ?p)) 
		   			(full ?s) (have_soil_analysis ?x ?p)
		   		)
	)
	
	(:action sample_rock
	     :parameters (?x - rover ?s - store ?p - waypoint)
	     :precondition (and
	     					(at ?x ?p) (at_rock_sample ?p) (equipped_for_rock_analysis ?x) 
	     					(store_of ?s ?x) (empty ?s)
	     				)
	     :effect (and 
		   			(not(empty ?s)) (not(at_rock_sample ?p)) 
		   			(full ?s) (have_rock_analysis ?x ?p)
		   		)
	)
          
  	(:action drop
	     :parameters (?x - rover ?s - store)
	     :precondition (and 
	     					(store_of ?s ?x) (full ?s)
	     				)
	     :effect (and 
	     			(not(full ?s)) (empty ?s)
				 )
	)
	
	(:action calibrate 
	     :parameters (?x - rover ?c - camera ?o - objective ?p - waypoint)
	     :precondition (and
	     					(equipped_for_imaging ?x) (calibration_target ?c ?o)
  							(at ?x ?p) (visible_from ?o ?p) (on_board ?c ?x)
	     				)
	     :effect (and 
  					(calibrated ?c ?x)
		   		)
	)
	
	(:action take_image
	     :parameters (?x - rover ?p - waypoint ?o - objective ?c - camera ?m - mode)
	     :precondition (and
	     					(calibrated ?c ?x) (on_board ?c ?x)
	     					(equipped_for_imaging ?x) (supports ?c ?m) 
	     					(at ?x ?p) (visible_from ?o ?p) 
	     				)
	     :effect (and 
  					(not(calibrated ?c ?x)) (have_image ?x ?o ?m)
		   		)
	)
	
	(:action communicate_soil_data1
	     :parameters (?x - rover ?l - lander ?p1 - waypoint ?p2 - waypoint ?p3 - waypoint)
	     :precondition (and
	     					(at ?x ?p2) (at_lander ?l ?p3)
	     					(have_soil_analysis ?x ?p1) (visible ?p2 ?p3)
	     					(available ?x) (channel_free ?l)
	     				)
	     :effect (and 
  					 (communicated_soil_data ?p1) (available ?x)
		   		)
	)
	
	(:action communicate_soil_data2
	     :parameters (?x - rover ?l - lander ?p1 - waypoint ?p2 - waypoint)
	     :precondition (and
	     					(at ?x ?p1) (at_lander ?l ?p2)
	     					(have_soil_analysis ?x ?p1) (visible ?p1 ?p2)
	     					(available ?x) (channel_free ?l)
	     				)
	     :effect (and 
  					 (communicated_soil_data ?p1) (available ?x)
		   		)
	)
	
	(:action communicate_rock_data1
	     :parameters (?x - rover ?l - lander ?p1 - waypoint ?p2 - waypoint ?p3 - waypoint)
	     :precondition (and
	     					(at ?x ?p2) (at_lander ?l ?p3)
	     					(have_rock_analysis ?x ?p1) (visible ?p2 ?p3)
	     					(available ?x) (channel_free ?l)
	     				)
	     :effect (and 
  					(communicated_rock_data ?p1) (available ?x)
		   		)
	)
	
	(:action communicate_rock_data2
	     :parameters (?x - rover ?l - lander ?p1 - waypoint ?p2 - waypoint)
	     :precondition (and
	     					(at ?x ?p1) (at_lander ?l ?p2)
	     					(have_rock_analysis ?x ?p1) (visible ?p1 ?p2)
	     					(available ?x) (channel_free ?l)
	     				)
	     :effect (and 
  					(communicated_rock_data ?p1) (available ?x)
		   		)
	)
	
	(:action communicate_image_data
	     :parameters (?x - rover ?l - lander ?o - objective ?m - mode ?p1 - waypoint ?p2 - waypoint)
	     :precondition (and
	     					(at ?x ?p1) (at_lander ?l ?p2)
	     					(have_image ?x ?o ?m) (visible ?p1 ?p2)
	     					(available ?x) (channel_free ?l)
	     				)
	     :effect (and 
  					(communicated_image_data ?o ?m) (available ?x)
  					(channel_free ?l)
		   		)
	)                
)
