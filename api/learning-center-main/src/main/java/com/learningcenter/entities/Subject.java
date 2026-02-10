package com.learningcenter.entities;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="subject_id")
    private long subject_id;

    @Column(nullable=false, name="name")
    private String name;

    @OneToMany(mappedBy = "subject", cascade=CascadeType.ALL)
    private ArrayList<TutorSubject> tutorSubjects = new ArrayList<>();

    public ArrayList<TutorSubject> getTutorSubjects() {
        return tutorSubjects;
    }

    public void setTutorSubjects(ArrayList<TutorSubject> tutorSubjects) {
        this.tutorSubjects = tutorSubjects;
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
