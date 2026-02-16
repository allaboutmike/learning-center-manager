package com.learningcenter.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tutor_time_slot", schema = "session")
public class TutorTimeslot {

    @Id
    @Column(name = "tutor_time_slot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutor_timeslot_id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @ManyToOne(optional=false)
    @JoinColumn(name = "time_slot_id", nullable = false)
    private Timeslot timeslot;

    public TutorTimeslot(Tutor tutor, Timeslot timeslot) {
        this.tutor = tutor;
        this.timeslot = timeslot;
    }

    public TutorTimeslot() {
    }

    public Long getTutor_timeslot_id() {
        return tutor_timeslot_id;
    }

    public void setTutor_timeslot_id(Long tutor_timeslot_id) {
        this.tutor_timeslot_id = tutor_timeslot_id;
    }

    public Tutor getTutor() {
       return tutor;
   }

   public void setTutor(Tutor tutor) {
       this.tutor= tutor;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }
}
