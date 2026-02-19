package com.learningcenter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learningcenter.dto.TutorResponse;
import com.learningcenter.service.TutorService;

@RestController
@RequestMapping("/api/children")
public class ChildController {
    private final TutorService tutorService;

    public ChildController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping("/{childId}/tutors")
    public List<TutorResponse> getTutorsForChild(@PathVariable Long childId) {
        if (childId == null) {
            return tutorService.getAllTutors();
        }
        return tutorService.searchTutorsByChildGradeLevel(childId);
    }
}