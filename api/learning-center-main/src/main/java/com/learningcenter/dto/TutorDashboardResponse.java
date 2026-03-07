package com.learningcenter.dto;

public record TutorDashboardResponse (
    String tutorName,
    Long totalStudentsTutored,
    Long totalSessionsCompleted,
    Double averageRating,
    SessionResponse nextUpcomingSession
){}