package com.learningcenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.learningcenter.dto.SessionResponse;
import com.learningcenter.dto.SubjectResponse;
import com.learningcenter.dto.TutorDashboardResponse;
import com.learningcenter.dto.TutorResponse;
import com.learningcenter.dto.TutorTimeSlotResponse;
import com.learningcenter.entities.Tutor;
import com.learningcenter.repository.ReviewRepository;
import com.learningcenter.repository.SessionRepository;
import com.learningcenter.repository.TutorRepository;
import com.learningcenter.repository.TutorTimeSlotRepository;

@Service
public class TutorService {

    private TutorRepository tutorRepository;
    private ReviewRepository reviewRepository;
    private TutorTimeSlotRepository tutorTimeSlotRepository;
    private SessionRepository sessionRepository;

    public TutorService(TutorRepository tutorRepository, ReviewRepository reviewRepository, TutorTimeSlotRepository tutorTimeSlotRepository, SessionRepository sessionRepository) {
        this.tutorRepository = tutorRepository;
        this.reviewRepository = reviewRepository;
        this.tutorTimeSlotRepository = tutorTimeSlotRepository;
        this.sessionRepository = sessionRepository;
    }

    private List<SubjectResponse> getSubjectResponses(Tutor tutor) {
        return tutor.getSubjects().stream()
            .map(s -> new SubjectResponse(s.getSubjectId(), s.getName()))
            .collect(Collectors.toList());
    }

    public List<TutorResponse> searchTutorsByChildGradeLevel(Long childId) {
        var tutors = tutorRepository.findTutorsByChildGradeLevel(childId);
        List<TutorResponse> tutorResponses = new ArrayList<>();
        for (Tutor tutor : tutors) {
            Double rating = reviewRepository.findByAvgRating(tutor.getTutorId());
            Double averageRating = rating != null ? rating : 0.0;

            tutorResponses.add(new TutorResponse(tutor.getTutorId(), tutor.getName(), averageRating, 
                reviewRepository.getNumberOfReviews(tutor.getTutorId()), tutor.getMinGradeLevel(), 
                tutor.getMaxGradeLevel(), tutor.getImageUrl(), getSubjectResponses(tutor)));
        }
        return tutorResponses;
    }

    public TutorResponse getTutorDetails(Long tutorId) {
        var tutor = tutorRepository.findById(tutorId);
        if (tutor.isEmpty()) {
            throw new RuntimeException("Tutor not found");
        }
        return new TutorResponse(tutorId, tutor.get().getName(), reviewRepository.findByAvgRating(tutorId), 
            reviewRepository.getNumberOfReviews(tutorId), tutor.get().getMinGradeLevel(), 
            tutor.get().getMaxGradeLevel(), tutor.get().getImageUrl(), getSubjectResponses(tutor.get()));
    }


    public List<TutorTimeSlotResponse> getTutorAvailability(Long tutorId) {
        var timeSlots = tutorTimeSlotRepository.findAvailableByTutorId(tutorId);
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
            Double rating = reviewRepository.findByAvgRating(tutor.getTutorId());
            Double averageRating = rating != null ? rating : 0.0;
            tutorResponses.add(new TutorResponse(tutor.getTutorId(), tutor.getName(), averageRating, 
                reviewRepository.getNumberOfReviews(tutor.getTutorId()), 
                tutor.getMinGradeLevel(), tutor.getMaxGradeLevel(), tutor.getImageUrl(), getSubjectResponses(tutor)));
        }
        return tutorResponses;
    }

    public List<TutorResponse> searchTutorsByGradeLevel(Integer gradeLevel) {
        var tutors = tutorRepository.findTutorsByGradeLevel(gradeLevel);
        List<TutorResponse> tutorResponses = new ArrayList<>();
        for (Tutor tutor : tutors) {
            Double rating = reviewRepository.findByAvgRating(tutor.getTutorId());
            Double averageRating = rating != null ? rating : 0.0;
            tutorResponses.add(new TutorResponse(tutor.getTutorId(), tutor.getName(), averageRating, 
                reviewRepository.getNumberOfReviews(tutor.getTutorId()), tutor.getMinGradeLevel(), 
                tutor.getMaxGradeLevel(), tutor.getImageUrl(), getSubjectResponses(tutor)));
        }
        return tutorResponses;
    }

    public List<SessionResponse> getTutorUpcomingSessions(Long tutorId) {
        var now = java.time.LocalDateTime.now();
        var sessions = sessionRepository.findSessionsByTutorId(tutorId);
        List<SessionResponse> responseList = new ArrayList<>();
        for (var session : sessions) {
            var tutorTimeslot = session.getTimeslot();
            if (tutorTimeslot == null || tutorTimeslot.getTimeslot() == null) {
                continue;
            }
            var sessionTime = tutorTimeslot.getTimeslot().getTime();
            if (sessionTime != null && sessionTime.isAfter(now)) {
                responseList.add(new SessionResponse(session));
            }
        }
        // Sort by time ascending
        responseList.sort((a, b) -> a.getTime().compareTo(b.getTime()));
        return responseList;
    }

    public List<SessionResponse> getTutorPastSessions(Long tutorId) {
        var now = java.time.LocalDateTime.now();
        var sessions = sessionRepository.findSessionsByTutorId(tutorId);
        List<SessionResponse> responseList = new ArrayList<>();
        for (var session : sessions) {
            var tutorTimeslot = session.getTimeslot();
            if (tutorTimeslot == null || tutorTimeslot.getTimeslot() == null) {
                continue;
            }
            var sessionTime = tutorTimeslot.getTimeslot().getTime();
            if (sessionTime != null && sessionTime.isBefore(now)) {
                responseList.add(new SessionResponse(session));
            }
        }
        // Sort by time descending (most recent first)
        responseList.sort((a, b) -> b.getTime().compareTo(a.getTime()));
        return responseList;
    }

    public TutorDashboardResponse getTutorDashboard(Long tutorId) {
        var tutor = tutorRepository.findById(tutorId);
        if (tutor.isEmpty()) {
            throw new RuntimeException("Tutor not found");
        }

        var sessions = sessionRepository.findSessionsByTutorId(tutorId);
        var uniqueStudents = sessions.stream()
            .map(s -> s.getChild().getChildId())
            .distinct()
            .count();

        var completedSessions = getTutorPastSessions(tutorId).size();
        var avgRating = reviewRepository.findByAvgRating(tutorId);
        if (avgRating == null) avgRating = 0.0;

        SessionResponse nextSession = null;
        var upcoming = getTutorUpcomingSessions(tutorId);
        if (!upcoming.isEmpty()) {
            nextSession = upcoming.get(0); // assuming sorted by time
        }

        return new TutorDashboardResponse(
            tutor.get().getName(),
            (long) uniqueStudents,
            (long) completedSessions,
            avgRating,
            nextSession
        );
    }
}
