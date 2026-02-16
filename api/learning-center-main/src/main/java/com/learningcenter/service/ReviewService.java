package com.learningcenter.service;

import com.learningcenter.dto.CreateReviewRequest;
import com.learningcenter.dto.ReviewResponse;
import com.learningcenter.entities.Review;
import com.learningcenter.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewResponse createReview(CreateReviewRequest request) {
        Review review = new Review();
        review.setTutor(request.getTutor());
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);

        return mapToDTO(savedReview);
    }

    public Optional<ReviewResponse> findByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(this::mapToDTO);
    }

    public List<ReviewResponse> findByTutorId(Long tutorId) {
        return reviewRepository.findByTutorId(tutorId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ReviewResponse mapToDTO(Review review) {
        ReviewResponse dto = new ReviewResponse();
        dto.setReviewId(review.getReviewId());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());

        return dto;
    }
}
