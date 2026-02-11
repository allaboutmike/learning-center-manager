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
@Table(name = "tutor", schema="tutor_profile")
public class Tutor {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tutor_id")
    private long tutor_id;

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

    @OneToMany(mappedBy = "tutor", cascade=CascadeType.ALL)
    private ArrayList<TutorSubject> tutorSubjects = new ArrayList<>();

    @OneToMany(mappedBy = "tutor", cascade=CascadeType.ALL)
    private ArrayList<Review> reviews = new ArrayList<>();


    public ArrayList<TutorSubject> getTutorSubjects() {
        return tutorSubjects;
    }

    public void setTutorSubjects(ArrayList<TutorSubject> tutorSubjects) {
        this.tutorSubjects = tutorSubjects;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public long getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(long tutor_id) {
        this.tutor_id = tutor_id;
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
