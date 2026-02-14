package com.learningcenter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session", schema = "session")
public class Session {

    @Column(name="session_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long session_id;

    @Column(nullable = false, name="session_notes")
    private String session_notes;

    @Column(nullable = false, name = "created_at")
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_timeslot_id")
    private TutorTimeslot tutor_timeslot;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Session(Long childId, Long sessionId, Long tutorId, String sessionNotes, LocalDateTime startTime, LocalDateTime endTime, String subject) {
    }

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
        this.child = child;
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
