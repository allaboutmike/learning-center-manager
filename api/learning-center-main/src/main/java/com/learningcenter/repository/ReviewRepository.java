package com.learningcenter.repository;

import com.learningcenter.dto.TutorResponse;
import com.learningcenter.entities.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query("SELECT tutor FROM Review WHERE tutor.tutorId =:tutorId")
    List<Review> findByTutorId(Long tutorId);

    @Query("SELECT tutor FROM Review WHERE tutor.tutorId =:tutorId AND tutor.reviews =:reviewId")
    TutorResponse findByTutorIdAndReviewId(Long tutorId, Long reviewId);
}
