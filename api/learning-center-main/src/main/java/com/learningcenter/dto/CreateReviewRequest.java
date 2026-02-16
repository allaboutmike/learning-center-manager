package com.learningcenter.dto;

import com.learningcenter.entities.Tutor;

import java.time.LocalDateTime;

public class CreateReviewRequest {

    private Long tutorId;
    private Long studentId;
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
    private Tutor tutor;

    public CreateReviewRequest(){}

    public CreateReviewRequest(Long tutorId, Tutor tutor, Long studentId, String comment, Integer rating) {
        this.tutorId = tutorId;
        this.tutor = tutor;
        this.studentId = studentId;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
