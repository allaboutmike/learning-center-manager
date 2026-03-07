package com.learningcenter.controller;


import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.learningcenter.dto.*;
import com.learningcenter.service.*;


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
    public List<ReviewResponse> getAllReviewsForTutor(@PathVariable Long tutorId) {
        return reviewService.findByTutorId(tutorId);
    }

    @GetMapping("/{tutorId}/dashboard")
    public TutorDashboardResponse getTutorDashboard(@PathVariable Long tutorId) {
        return tutorService.getTutorDashboard(tutorId);
    }

    @GetMapping("/{tutorId}/sessions/upcoming")
    public List<SessionResponse> getTutorUpcomingSessions(@PathVariable Long tutorId) {
        return tutorService.getTutorUpcomingSessions(tutorId);
    }

    @GetMapping("/{tutorId}/sessions/past")
    public List<SessionResponse> getTutorPastSessions(@PathVariable Long tutorId) {
        return tutorService.getTutorPastSessions(tutorId);
    }
}
