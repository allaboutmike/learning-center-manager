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
@Table(name = "tutor_subject", schema = "tutor_profile")
public class TutorSubject {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tutor_subject_id")
    private long tutor_subject_id;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public long getTutor_subject_id() {
        return tutor_subject_id;
    }

    public void setTutor_subject_id(long tutor_subject_id) {
        this.tutor_subject_id = tutor_subject_id;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
