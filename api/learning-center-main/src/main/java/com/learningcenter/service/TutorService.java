package com.learningcenter.service;

import com.learningcenter.dto.TutorResponse;
import com.learningcenter.dto.TutorTimeSlotResponse;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TutorService {

    private final List<TutorResponse> tutors = List.of(new TutorResponse(11L, "Stitch Doe", 4.5, 10, 5, 10, "/stitch.png"), new TutorResponse(12L, "Sailor Smith", 4.7, 15, 1, 6, "/sailor_moon.png"));

    public List<TutorResponse> searchTutorsByGradeLevel(Integer gradeLevel) {
        if (gradeLevel == null) {
            return tutors;
        }
        List<TutorResponse> filteredTutor = new ArrayList<>();
        for (TutorResponse tutor : tutors) {
            if (gradeLevel >= tutor.getMinGradeLevel() && gradeLevel <= tutor.getMaxGradeLevel()) {
                filteredTutor.add(tutor);
            }
        }
        return filteredTutor;
    }

    public TutorResponse getTutorDetails(Long tutorId) {
        return new TutorResponse(11L, "Stitch Doe", 4.5, 10, 5, 10, "/stitch.png");

    }

    public List<TutorTimeSlotResponse> getTutorAvailability(Long tutorId) {
        var fivePmSlotStart = LocalDateTime.of(2026, 2, 14, 17, 0);
        var sixPmSlotStart = LocalDateTime.of(2026, 2, 14, 18, 0);

        return List.of(new TutorTimeSlotResponse(101L, tutorId, 201L, fivePmSlotStart, fivePmSlotStart.plusHours(1)), new TutorTimeSlotResponse(102L, tutorId, 202L, sixPmSlotStart, sixPmSlotStart.plusHours(1)));
    }
}
