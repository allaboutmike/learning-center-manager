package com.learningcenter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SessionResponse {
    /*
    SessionResponse Object
    Handles all session responses for session searches via sessionId.
    */
    private Long sessionId;
    private Long tutorId;
    private String subject;
    private String sessionNotes;
    private String status;
    private Long childId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    public SessionResponse(Long sessionId) {
    }

    public Long getSessionId() {
        return sessionId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public Long getChildId() {
        return childId;
    }

    public String getStatus() {
        return status;
    }
}
