package com.learningcenter.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "review", schema = "review")
public class Review {

    @Column(name = "review_id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false, name = "comment")
    private String comment;

    @Column(nullable = false, name = "rating")
    private int rating;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional=false)
    @JoinColumn(name= "tutor_id", nullable = false)
    private Tutor tutor;

    public Review(String comment, int rating, Tutor tutor, LocalDateTime createdAt) {
        this.comment = comment;
        this.rating = rating;
        this.tutor = tutor;
        this.createdAt = createdAt;
    }

    public Review() {
        
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
