package com.learningcenter.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Column(name="session_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sessionId;

    @Column(nullable = false, name="session_notes")
    private String sessionNotes;

    @Column(nullable = false, name = "createdAt")
    private LocalDateTime createdAt;

    @ManyToOne(optional=false)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_time_slot_id", nullable = false)
    private TutorTimeslot tutorTimeslot;

    @ManyToOne(optional=false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    public Session(String session_notes, LocalDateTime createdAt, Child child, TutorTimeslot tutorTimeslot, Subject subject) {
        this.sessionNotes = sessionNotes;
        this.createdAt = createdAt;
        this.child = child;
        this.tutorTimeslot = tutorTimeslot;
        this.subject = subject;
    }

    public Session() {

    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionNotes() {
        return sessionNotes;
    }

    public void setSessionNotes(String sessionNotes) {
        this.sessionNotes = sessionNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public TutorTimeslot getTimeslot() {
        return tutorTimeslot;
    }

    public void setTimeslot_id(TutorTimeslot tutorTimeslot) {
        this.tutorTimeslot = tutorTimeslot;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}