package com.learningcenter.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tutor", schema="tutor_profile")
public class Tutor {

    public Tutor(String name, int minGradeLevel, int maxGradeLevel, String url, String summary) {
        this.name = name;
        this.minGradeLevel = minGradeLevel;
        this.maxGradeLevel = maxGradeLevel;
        this.url = url;
        this.summary = summary;
    }

    public Tutor() {
        
    }

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
    @JoinTable(name = "tutor_subject", schema="tutor_profile", joinColumns = @JoinColumn(name = "tutor_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "tutor", cascade=CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "tutor", cascade=CascadeType.ALL)
    private List<TutorTimeslot> tutorTimeSlots = new ArrayList<>();

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Subject> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<Subject> subjects) {
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

    public List<TutorTimeslot> getTutorTimeSlots() {
        return tutorTimeSlots;
    }

    public void setTutorTimeSlots(List<TutorTimeslot> tutorTimeSlots) {
        this.tutorTimeSlots = tutorTimeSlots;
    }
}