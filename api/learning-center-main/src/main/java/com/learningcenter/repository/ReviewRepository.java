package com.learningcenter.repository;

import com.learningcenter.dto.TutorResponse;
import com.learningcenter.entities.Review;
import com.learningcenter.entities.Tutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query("SELECT tutor FROM Review WHERE tutor.tutorId =:tutorId")
    List<Review> findByTutorId(Long tutorId);

    @Query("SELECT tutor FROM Review GROUP BY tutor HAVING AVG(rating) =:avgRating")
    List<Tutor> findByAvgRating(@Param("avgRating") Double avgRating);

    @Query ("SELECT COUNT(tutor.tutorId) FROM Review WHERE tutor.tutorId =:tutorId")
    List<Tutor> findByReviewCount(@Param("reviewCount") Integer reviewCount);
}