package com.learningcenter.dto;

import com.learningcenter.entities.Tutor;

import java.time.LocalDateTime;

public class ReviewResponse {
    private Long tutorId;
    private String comment;
    private Long reviewId;
    private Integer rating;
    private Tutor tutor;
    private LocalDateTime createdAt;

    public ReviewResponse(){

    }

    public ReviewResponse(Long reviewId, Long tutorId, Tutor tutor, Long studentId, String comment, Integer rating, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.tutorId = tutorId;
        this.tutor = tutor;
        this.comment = comment;
        this.rating =rating;
        this.createdAt = createdAt;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public String getComment() {
        return comment;
    }

    public Integer getRating() {
        return rating;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

}