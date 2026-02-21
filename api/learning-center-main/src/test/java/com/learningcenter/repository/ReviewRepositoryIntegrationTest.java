package com.learningcenter.repository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.learningcenter.entities.Review;
import com.learningcenter.entities.Tutor;

@DataJpaTest
public class ReviewRepositoryIntegrationTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void shouldFindTutorWithReviewCount() {
        Tutor tutor = new Tutor("Jane", 2, 4, "example.com", "testing 123");
        Review review1 = new Review("testing 123", 4, tutor, LocalDateTime.now());
        Review review2 = new Review("testing 123", 4, tutor, LocalDateTime.now());
        Review review3 = new Review("testing 123", 4, tutor, LocalDateTime.now());
        tutor.getReviews().add(review1);
        tutor.getReviews().add(review2);
        tutor.getReviews().add(review3);

        entityManager.persist(tutor);
        entityManager.persist(review1);
        entityManager.persist(review2);
        entityManager.persist(review3);

        assertThat(tutor.getReviews().size()).isEqualTo(3);
    }

    @Test
    void shouldFindTutorWithAverageRating() {
        Tutor tutor = new Tutor("John", 5, 7, "example.com", "testing 123");
        Review review = new Review("testing 123", 4, tutor, LocalDateTime.now());
        tutor.getReviews().add(review);

        entityManager.persist(tutor);
        entityManager.persist(review);
        entityManager.flush();
        entityManager.clear();

        assertThat(review.getRating()).isEqualTo(4);
    }
}
