package com.learningcenter.dto;

import com.learningcenter.entities.Session;

import java.time.LocalDateTime;

public class SessionResponse {
    /*
    SessionResponse Object
    Handles all session responses for session searches via sessionId.
    */
    private Long sessionId;
    private Long tutorId;
    private Long subjectId;
    private String sessionNotes;
    private Long childId;
    private LocalDateTime time;

    public SessionResponse(Session session) {
        this.sessionId = session.getSessionId();
        this.tutorId = session.getTimeslot().getTutor().getTutorId();
        this.subjectId = session.getSubject().getSubjectId();
        this.sessionNotes = session.getSessionNotes();
        this.childId = session.getChild().getChildId();
        this.time = session.getTimeslot().getTimeslot().getTime();
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public Long getChildId() {
        return childId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
