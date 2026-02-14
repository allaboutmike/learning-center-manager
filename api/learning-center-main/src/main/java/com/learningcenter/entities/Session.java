package com.learningcenter.entities;

import java.sql.Timestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "session", schema = "session")
public class Session {

    public Session(String session_notes, Timestamp timestamp, Child child, TutorTimeslot tutor_timeslot, Subject subject) {
        this.session_notes = session_notes;
        this.timestamp = timestamp;
        this.child = child;
        this.tutor_timeslot = tutor_timeslot;
        this.subject = subject;
    }

    public Session() {
        
    }

    @Column(name="session_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long session_id;

    @Column(nullable = false, name="session_notes")
    private String session_notes;

    @Column(nullable = false, name = "created_at")
    private Timestamp timestamp;

    @ManyToOne(optional=false)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_time_slot_id", nullable=false)
    private TutorTimeslot tutor_timeslot;

    @ManyToOne(optional=false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    public long getSession_id() {
        return session_id;
    }

    public void setSession_id(long session_id) {
        this.session_id = session_id;
    }

    public String getSession_notes() {
        return session_notes;
    }

    public void setSession_notes(String session_notes) {
        this.session_notes = session_notes;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Child getChild_id() {
        return child;
    }

    public void setChild_id(Child child_id) {
        this.child = child_id;
    }

    public TutorTimeslot getTimeslot_id() {
        return tutor_timeslot;
    }

    public void setTimeslot_id(TutorTimeslot tutor_timeslot) {
        this.tutor_timeslot = tutor_timeslot;
    }

    public Subject getSubjet_id() {
        return subject;
    }

    public void setSubjet_id(Subject subject) {
        this.subject = subject;
    }
}
