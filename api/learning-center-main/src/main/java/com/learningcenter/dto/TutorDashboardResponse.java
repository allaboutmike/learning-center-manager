package com.learningcenter.dto;

public class TutorDashboardResponse {
    private String tutorName;
    private Long totalStudentsTutored;
    private Long totalSessionsCompleted;
    private Double averageRating;
    private SessionResponse nextUpcomingSession;

    public TutorDashboardResponse(String tutorName, Long totalStudentsTutored, Long totalSessionsCompleted, Double averageRating, SessionResponse nextUpcomingSession) {
        this.tutorName = tutorName;
        this.totalStudentsTutored = totalStudentsTutored;
        this.totalSessionsCompleted = totalSessionsCompleted;
        this.averageRating = averageRating;
        this.nextUpcomingSession = nextUpcomingSession;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public Long getTotalStudentsTutored() {
        return totalStudentsTutored;
    }

    public void setTotalStudentsTutored(Long totalStudentsTutored) {
        this.totalStudentsTutored = totalStudentsTutored;
    }

    public Long getTotalSessionsCompleted() {
        return totalSessionsCompleted;
    }

    public void setTotalSessionsCompleted(Long totalSessionsCompleted) {
        this.totalSessionsCompleted = totalSessionsCompleted;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public SessionResponse getNextUpcomingSession() {
        return nextUpcomingSession;
    }

    public void setNextUpcomingSession(SessionResponse nextUpcomingSession) {
        this.nextUpcomingSession = nextUpcomingSession;
    }
}