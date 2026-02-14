package com.learningcenter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CreateSessionRequest {
    /*
    CreateSessionRequest Object
    Handles the creation of new sessions.
    */

    private final Long sessionId;
    private final Long tutorId;
    private final String subject;
    private final String sessionNotes;
    private final Long childId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime endTime;

    public CreateSessionRequest(Long sessionId, Long tutorId, Long childId, String subject, String sessionNotes, LocalDateTime startTime, LocalDateTime endTime) {
        this.sessionId = sessionId;
        this.tutorId = tutorId;
        this.subject = subject;
        this.sessionNotes = sessionNotes;
        this.childId = childId;
        this.startTime = LocalDateTime.now();
        this.endTime = LocalDateTime.now();
    }

    public String getSessionNotes() {
        return sessionNotes;
    }

    public void setSessionNotes(String sessionNotes) {
        // Need logic here
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        // Need logic here
    }

    public Long getTutorId() {
        return tutorId;
    }

    public Long getChildId() {
        return childId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        // Need logic here
    }


}
