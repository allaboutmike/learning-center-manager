package com.learningcenter.controller;


import com.learningcenter.dto.TutorResponse;
import com.learningcenter.service.TutorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {
    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }


    @GetMapping
    public List<TutorResponse> searchTutorsByGradeLevel(@RequestParam Integer gradeLevel) {
        return tutorService.searchTutorsByGradeLevel(gradeLevel);
    }

    @GetMapping("/{tutorId}")
    public Object getTutorDetails(@PathVariable Long tutorId) {
        return tutorService.getTutorDetails(tutorId);
    }


}
