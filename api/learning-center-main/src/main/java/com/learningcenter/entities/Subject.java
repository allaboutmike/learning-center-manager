package com.learningcenter.entities;

import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@Table(name = "subject", schema = "tutor_profile")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="subject_id")
    private long subject_id;

    @Column(nullable=false, name="name")
    private String name;

    @ManyToMany(mappedBy = "subjects")
    private ArrayList<Tutor> tutors = new ArrayList<>();

//    @OneToMany(mappedBy = "session")
//    private ArrayList<Session> subjects = new ArrayList<>();

    public ArrayList<Tutor> getTutor() {
        return this.tutors;
    }

    public void setTutors(ArrayList<Tutor> tutors) {
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
