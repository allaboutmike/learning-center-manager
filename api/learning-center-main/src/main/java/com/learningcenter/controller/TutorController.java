package com.learningcenter.controller;


import com.learningcenter.dto.TutorResponse;
import com.learningcenter.dto.TutorTimeSlotResponse;
import com.learningcenter.service.TutorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {
    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }


    @GetMapping
    public List<TutorResponse> searchTutorsByGradeLevel(@RequestParam(required = false) Integer gradeLevel) {
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

}
