package com.learningcenter.controller;


import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learningcenter.dto.CreateReviewRequest;
import com.learningcenter.dto.ReviewResponse;
import com.learningcenter.service.ReviewService;


@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse createReview(@RequestBody CreateReviewRequest request) {

        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response).getBody();

    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long reviewId) {

        Optional<ReviewResponse> response = reviewService.findByReviewId(reviewId);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tutor/{tutorId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForTutor(@PathVariable Long tutorId) {
        double averageRating = reviewService.findByAvgRating(tutorId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/tutor/{tutorId}/number-of-reviews")
    public ResponseEntity<Long> getNumberOfReviewsForTutor(@PathVariable Long tutorId) {
        long numberOfReviews = reviewService.getNumberOfReviews(tutorId);
        return ResponseEntity.ok(numberOfReviews);
    }
}