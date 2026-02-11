package com.learningcenter.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "tutor_timeslot", schema = "session")
public class TutorTimeslot {

    @Id
    @Column(name = "tutor_timeslot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tutor_timeslot_id;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "timeslot_id")
    private Timeslot timeslot;

    public long getTutor_timeslot_id() {
        return tutor_timeslot_id;
    }

    public void setTutor_timeslot_id(long tutor_timeslot_id) {
        this.tutor_timeslot_id = tutor_timeslot_id;
    }

    public Tutor getTutor_id() {
       return tutor;
   }

   public void setTutor_id(Tutor tutor) {
       this.tutor= tutor;
    }

    public Timeslot getTimeslot_id() {
        return timeslot;
    }

    public void setTimeslot_id(Timeslot timeslot) {
        this.timeslot = timeslot;
    }
}
