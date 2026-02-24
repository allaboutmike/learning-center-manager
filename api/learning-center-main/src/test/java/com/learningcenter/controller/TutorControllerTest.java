package com.learningcenter.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.learningcenter.dto.ReviewResponse;
import com.learningcenter.dto.TutorResponse;
import com.learningcenter.dto.TutorTimeSlotResponse;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class TutorControllerTest {

    @Autowired
    private TutorController tutorController;

    @Autowired
    private ChildController childController;

    @Test
    void searchTutorByChildGradeLevelTest_returnTutorList() {
        List<TutorResponse> tutors = childController.getTutorsForChild(1L);
        assertNotNull(tutors);
        assertEquals(7, tutors.size());
    }

    @Test
    void getTutorDetailsTest_returnTutorDetails() {
        TutorResponse tutor = tutorController.getTutorDetails(1L);
        assertNotNull(tutor);
        assertEquals(1L, tutor.getTutorId());
    }

    @Test
    void getTutorAvailabilityTest_returnTutorAvailability() {
        List<TutorTimeSlotResponse> availability = tutorController.getTutorAvailability(1L);
        assertNotNull(availability);
        assertEquals(2, availability.size());
    }

    @Test
    void searchTutorByGradeLevelTest_returnAllTutors() {
        List<TutorResponse> tutors = childController.getTutorsForChild(null);
        assertNotNull(tutors);
        assertEquals(15, tutors.size());
    }

    @Test
    void searchTutorByGradeLevelTest_returnTutorsForChild() {
        List<TutorResponse> tutors = tutorController.searchTutorsByGradeLevel(1);
        assertNotNull(tutors);
        assertEquals(1, tutors.size());
    }

    @Test
    void getReviewsForTutorTest_returnTutorReviews() {
        List<ReviewResponse> reviews = tutorController.getAllReviewsForTutor(1L);
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
    }
}
