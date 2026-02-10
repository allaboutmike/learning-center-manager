package com.learningcenter.service;

import com.learningcenter.dto.TutorResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {
    public List<TutorResponse> searchTutorsByGradeLevel(Integer gradeLevel) {
        return List.of(new TutorResponse(11L, "John Doe", 4.5, 10));
    }

    public TutorResponse getTutorDetails(Long tutorId) {
        return new TutorResponse(tutorId, "John Doe", 4.5, 10);
    }

}
