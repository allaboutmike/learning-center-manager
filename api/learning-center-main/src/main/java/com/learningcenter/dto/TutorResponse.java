package com.learningcenter.dto;

public class TutorResponse {

    private Long tutorId;
    private String name;
    private double avgRating;
    private int reviewCount;

    public TutorResponse(Long tutorId, String name, double avgRating, int reviewCount) {
        this.tutorId = tutorId;
        this.name = name;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
    }

    public Long getTutorId() {
        return tutorId;
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
}

