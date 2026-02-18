package com.learningcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learningcenter.entities.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query("SELECT tutor FROM Review WHERE tutor.tutorId =:tutorId")
    List<Review> findByTutorId(Long tutorId);

    @Query("SELECT AVG(rating) FROM Review WHERE tutor.tutorId =:tutorId")
    Double findByAvgRating(@Param("tutorId") Long tutorId);

    @Query ("SELECT COUNT(tutor.tutorId) FROM Review WHERE tutor.tutorId =:tutorId")
    Integer getNumberOfReviews(@Param("tutorId") Long tutorId);
}