package com.learningcenter.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "subject", schema = "tutor_profile")
public class Subject {

    public Subject(String name) {
        this.name = name;
    }

    public Subject() {
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="subject_id")
    private long subject_id;

    @Column(nullable=false, name="subject_name")
    private String name;

    @ManyToMany(mappedBy = "subjects")
    private List<Tutor> tutors = new ArrayList<>();

//    @OneToMany(mappedBy = "session")
//    private ArrayList<Session> subjects = new ArrayList<>();

    public List<Tutor> getTutors() {
        return tutors;
    }

    public void setTutors(List<Tutor> tutors) {
        this.tutors = tutors;
    }

    public long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(long subject_id) {
        this.subject_id = subject_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
