package com.learningcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.learningcenter.dto.TutorResponse;
import com.learningcenter.dto.TutorTimeSlotResponse;
import com.learningcenter.entities.Tutor;
import com.learningcenter.repository.ReviewRepository;
import com.learningcenter.repository.TutorRepository;
import com.learningcenter.repository.TutorTimeSlotRepository;

@Service
public class TutorService {

    private TutorRepository tutorRepository;
    private ReviewRepository reviewRepository;
    private TutorTimeSlotRepository tutorTimeSlotRepository;

    public TutorService(TutorRepository tutorRepository, ReviewRepository reviewRepository, TutorTimeSlotRepository tutorTimeSlotRepository) {
        this.tutorRepository = tutorRepository;
        this.reviewRepository = reviewRepository;
        this.tutorTimeSlotRepository = tutorTimeSlotRepository;
    }

    public List<TutorResponse> searchTutorsByGradeLevel(Long childId) {
        var tutors = tutorRepository.findTutorsByGradeLevel(childId);
        List<TutorResponse> tutorResponses = new ArrayList<>();
        for (Tutor tutor : tutors) {
            Double rating = reviewRepository.getAverageRating(tutor.getTutorId());
            Double averageRating = rating != null ? rating : 0.0;
            tutorResponses.add(new TutorResponse(tutor.getTutorId(), tutor.getName(), averageRating, 
                reviewRepository.getNumberOfReviews(tutor.getTutorId()), tutor.getMinGradeLevel(), 
                tutor.getMaxGradeLevel(), tutor.getImageUrl(), tutor.getSubjects()));
        }
        return tutorResponses;
    }

    public TutorResponse getTutorDetails(Long tutorId) {
        var tutor = tutorRepository.findById(tutorId);
        if (tutor.isEmpty()) {
            throw new RuntimeException("Tutor not found");
        }
        return new TutorResponse(tutorId, tutor.get().getName(), reviewRepository.getAverageRating(tutorId), 
            reviewRepository.getNumberOfReviews(tutorId), tutor.get().getMinGradeLevel(), 
            tutor.get().getMaxGradeLevel(), tutor.get().getImageUrl(), tutor.get().getSubjects());
    }

    public List<TutorTimeSlotResponse> getTutorAvailability(Long tutorId) {
        var timeSlots = tutorTimeSlotRepository.findByTutorId(tutorId);
        List<TutorTimeSlotResponse> responses = new ArrayList<>();
        for (var timeSlot : timeSlots) {
            responses.add(new TutorTimeSlotResponse(timeSlot.getTutorTimeslotId(), timeSlot.getTutor().getTutorId(), timeSlot.getTimeslot().getTimeslotId(), timeSlot.getTimeslot().getTime(), timeSlot.getTimeslot().getTime().plusHours(1)));
        }
        return responses;
    }

    public List<TutorResponse> getAllTutors() {
        var tutors = tutorRepository.findAll();
        List<TutorResponse> tutorResponses = new ArrayList<>();
        for (Tutor tutor : tutors) {
            Double rating = reviewRepository.getAverageRating(tutor.getTutorId());
            Double averageRating = rating != null ? rating : 0.0;
            tutorResponses.add(new TutorResponse(tutor.getTutorId(), tutor.getName(), averageRating, 
                reviewRepository.getNumberOfReviews(tutor.getTutorId()), 
                tutor.getMinGradeLevel(), tutor.getMaxGradeLevel(), tutor.getImageUrl(), tutor.getSubjects()));
        }
        return tutorResponses;
    }
}
