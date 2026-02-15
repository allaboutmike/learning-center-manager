package com.learningcenter.controller;

import com.learningcenter.dto.TutorResponse;
import com.learningcenter.dto.TutorTimeSlotResponse;
import com.learningcenter.service.TutorService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TutorControllerTest {
    @Test
    void searchTutorByGradeLevelTest_returnTutorList() {
        TutorService tutorService = new TutorService();
        TutorController tutorController = new TutorController(tutorService);

        List<TutorResponse> tutors = tutorController.searchTutorsByGradeLevel(10);
        assertNotNull(tutors);
        assertEquals(1, tutors.size());
    }

    @Test
    void getTutorDetailsTest_returnTutorDetails() {
        TutorService tutorService = new TutorService();
        TutorController tutorController = new TutorController(tutorService);

        TutorResponse tutor = (TutorResponse) tutorController.getTutorDetails(11L);
        assertNotNull(tutor);
        assertEquals(11L, tutor.getTutorId());
    }

    @Test
    void getTutorAvailabilityTest_returnTutorAvailability() {
        TutorService tutorService = new TutorService();
        TutorController tutorController = new TutorController(tutorService);

        List<TutorTimeSlotResponse> availability = tutorController.getTutorAvailability(11L);
        assertNotNull(availability);
        assertEquals(2, availability.size());
    }
}
