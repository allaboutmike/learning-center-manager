package com.learningcenter.dto;

import java.time.LocalDateTime;

import com.learningcenter.entities.Tutor;

public class ReviewResponse {
    private Long tutorId;
    private String comment;
    private Long reviewId;
    private Integer rating;
    private LocalDateTime createdAt;

    public ReviewResponse(){

    }

    public ReviewResponse(Long reviewId, Long tutorId, Tutor tutor, String comment, Integer rating, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.tutorId = tutorId;
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


}