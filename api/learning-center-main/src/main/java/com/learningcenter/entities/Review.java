package com.learningcenter.entities;

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
    private long review_id;

    @Column(nullable = false, name = "comment")
    private String comment;

    @Column(nullable = false, name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name= "tutor_id")
    private Tutor tutor;

    public long getReview_id() {
        return review_id;
    }

    public void setReview_id(long review_id) {
        this.review_id = review_id;
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

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
