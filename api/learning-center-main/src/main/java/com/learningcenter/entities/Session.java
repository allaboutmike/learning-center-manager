package com.learningcenter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;

import javax.security.auth.Subject;
import java.sql.Timestamp;

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
    private Child child_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_timeslot_id")
    private TutorTimeslot timeslot_id;

    // Does this session need cascade
//    @OneToOne
//    @JoinColumn(name = "tutor_subject_id")
//    private TutorSubject tutor_subjet_id;


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
        return child_id;
    }

    public void setChild_id(Child child_id) {
        this.child_id = child_id;
    }

    public TutorTimeslot getTimeslot_id() {
        return timeslot_id;
    }

    public void setTimeslot_id(TutorTimeslot timeslot_id) {
        this.timeslot_id = timeslot_id;
    }

//    public Subject getSubjet_id() {
//        return tutor_subjet_id;
//    }
//
//    public void setSubjet_id(Subject subjet_id) {
//        this.tutor_subjet_id = subjet_id;
//    }
}
