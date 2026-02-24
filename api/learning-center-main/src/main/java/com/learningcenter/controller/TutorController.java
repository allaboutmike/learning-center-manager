package com.learningcenter.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learningcenter.dto.ReviewResponse;
import com.learningcenter.dto.TutorResponse;
import com.learningcenter.dto.TutorTimeSlotResponse;
import com.learningcenter.service.ReviewService;
import com.learningcenter.service.TutorService;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {
    private final TutorService tutorService;
    private final ReviewService reviewService;

    public TutorController(TutorService tutorService, ReviewService reviewService) {
        this.tutorService = tutorService;
        this.reviewService = reviewService;
    }


    @GetMapping
    public List<TutorResponse> searchTutorsByGradeLevel(@RequestParam(required = false) Integer gradeLevel) {
        if (gradeLevel == null) {
            return tutorService.getAllTutors();
        }
        return tutorService.searchTutorsByGradeLevel(gradeLevel);
    }

    @GetMapping("/{tutorId}")
    public TutorResponse getTutorDetails(@PathVariable Long tutorId) {
        return tutorService.getTutorDetails(tutorId);
    }

    @GetMapping("/{tutorId}/availability")
    public List<TutorTimeSlotResponse> getTutorAvailability(@PathVariable Long tutorId) {
        return tutorService.getTutorAvailability(tutorId);
    }

    @GetMapping("/{tutorId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsForTutor(@PathVariable Long tutorId) {
        List<ReviewResponse> reviews = reviewService.findByTutorId(tutorId);
        return ResponseEntity.ok(reviews);
    }
}
