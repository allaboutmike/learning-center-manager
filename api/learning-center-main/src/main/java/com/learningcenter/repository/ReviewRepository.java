package com.learningcenter.repository;

import com.learningcenter.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Using Optional for catching NullPointerException
    Optional<Review> findByTutorId(Long tutorId);
}
