package com.learningcenter.entities;

import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@Table(name = "tutor", schema="tutor_profile")
public class Tutor {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tutor_id")
    private long tutorId;

    @Column(nullable=false,name="name")
    private String name;

    @Column(nullable=false, name="url")
    private String url;

    @Column(nullable=false, name="summary")
    private String summary;

    @Column(nullable=false, name="min_grade_level")
    private int minGradeLevel;

    @Column(nullable=false, name="max_grade_level")
    private int maxGradeLevel;

    //ArrayList?
    @ManyToMany
    @JoinTable(name = "tutor_subject", joinColumns = @JoinColumn(name = "tutor_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private ArrayList<Subject> subjects = new ArrayList<>();

    public ArrayList<Subject> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public long getTutorId() {
        return this.tutorId;
    }

    public void setTutorId(long tutorId) {
        this.tutorId = tutorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getMinGradeLevel() {
        return minGradeLevel;
    }

    public void setMinGradeLevel(int minGradeLevel) {
        this.minGradeLevel = minGradeLevel;
    }

    public int getMaxGradeLevel() {
        return maxGradeLevel;
    }

    public void setMaxGradeLevel(int maxGradeLevel) {
        this.maxGradeLevel = maxGradeLevel;
    }
    
}
