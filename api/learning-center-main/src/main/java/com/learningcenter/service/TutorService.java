package com.learningcenter.service;

import com.learningcenter.dto.TutorResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    private final List<TutorResponse> tutors = List.of(
            new TutorResponse(11L, "John Doe", 4.5, 10),
            new TutorResponse(12L, "Jane Smith", 4.7, 15)
    );

    public List<TutorResponse> searchTutorsByGradeLevel(Integer gradeLevel) {
        if (gradeLevel == null) {
            return tutors;
        }
        return List.of(new TutorResponse(11L, "John Doe", 4.5, 10));
    }

    public TutorResponse getTutorDetails(Long tutorId) {
        return new TutorResponse(tutorId, "John Doe", 4.5, 10);
    }

}
