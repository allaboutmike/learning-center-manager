package com.learningcenter.service;

import com.learningcenter.dto.TutorResponse;
import com.learningcenter.dto.TutorTimeSlotResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TutorService {

    private final List<TutorResponse> tutors = List.of(
            new TutorResponse(11L, "Stitch Doe", 4.5, 10, 5, 10, "/stitch.png"),
            new TutorResponse(12L, "Sailor Smith", 4.7, 15, 1, 6, "/sailor_moon.png")
    );

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
        List<TutorTimeSlotResponse> timeSlots = new ArrayList<>();
        timeSlots.add(new TutorTimeSlotResponse(1L, tutorId, 1L, LocalDateTime.of(2026, 2, 14, 17, 0), LocalDateTime.of(2026, 2, 14, 18, 0)));
        timeSlots.add(new TutorTimeSlotResponse(2L, tutorId, 2L, LocalDateTime.of(2026, 2, 16, 18, 0), LocalDateTime.of(2026, 2, 16, 19, 0)));
        return timeSlots;
    }
}
