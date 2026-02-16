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
    private Long tutorTimeslotId;

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

    public Long getTutorTimeslotId() {
        return tutorTimeslotId;
    }

    public void setTutorTimeslotId(Long tutorTimeslotId) {
        this.tutorTimeslotId = tutorTimeslotId;
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
