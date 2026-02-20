package com.learningcenter.dto;

import java.util.List;

public class TutorResponse {

    private Long tutorId;
    private String name;
    private double avgRating;
    private int reviewCount;
    private int minGradeLevel;
    private int maxGradeLevel;
    private String profilePictureUrl;
    private List<SubjectResponse> subjects;

    public TutorResponse(Long tutorId, String name, double avgRating, int reviewCount,int minGradeLevel, int maxGradeLevel, String profilePictureUrl, List<SubjectResponse> subjects) {
        this.tutorId = tutorId;
        this.name = name;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
        this.minGradeLevel = minGradeLevel;
        this.maxGradeLevel = maxGradeLevel;
        this.profilePictureUrl = profilePictureUrl;
        this.subjects = subjects;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Long getTutorId() {
        return tutorId;
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

    public String getName() {
        return name;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public List<SubjectResponse> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectResponse> subjects) {
        this.subjects = subjects;
    }
}

