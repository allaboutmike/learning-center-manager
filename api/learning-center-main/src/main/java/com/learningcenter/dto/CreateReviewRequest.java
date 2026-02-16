package com.learningcenter.dto;

import com.learningcenter.entities.Tutor;

import java.time.LocalDateTime;

public class CreateReviewRequest {

    private Long tutorId;
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
    private Tutor tutor;

    public CreateReviewRequest(){}

    public CreateReviewRequest(Long tutorId, Tutor tutor, String comment, Integer rating) {
        this.tutorId = tutorId;
        this.tutor = tutor;
        this.comment = comment;
        this.rating = rating;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
