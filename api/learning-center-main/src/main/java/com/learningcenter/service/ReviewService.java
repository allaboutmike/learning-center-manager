package com.learningcenter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learningcenter.dto.CreateReviewRequest;
import com.learningcenter.dto.ReviewResponse;
import com.learningcenter.entities.Review;
import com.learningcenter.repository.ReviewRepository;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewResponse createReview(CreateReviewRequest request) {
        Review review = new Review();
        review.setTutor(request.tutor());
        review.setRating(request.rating());
        review.setComment(request.comment());
        review.setCreatedAt(request.createdAt());

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

    public int getNumberOfReviews(Long tutorId) {
        return reviewRepository.getNumberOfReviews(tutorId);
    }

    public double findByAvgRating(Long tutorId) {
        Double rating = reviewRepository.findByAvgRating(tutorId);
        return rating != null ? rating : 0.0;
    }

    private ReviewResponse mapToDTO(Review review) {
        ReviewResponse dto = new ReviewResponse();
        dto.setReviewId(review.getReviewId());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setTutorId(review.getTutor().getTutorId());
        dto.setCreatedAt(review.getCreatedAt());

        return dto;
    }
}
