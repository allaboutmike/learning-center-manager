package com.learningcenter.dto;

import com.learningcenter.entities.Session;

public class SessionResponse {
    /*
    SessionResponse Object
    Handles all session responses for session searches via sessionId.
    */
    private Long sessionId;
    private Long tutorId;
    private Long subject;
    private String sessionNotes;
    private Long childId;

    public SessionResponse(Session session) {
        this.sessionId = session.getSession_id();
        this.tutorId = session.getTimeslot_id().getTutor_id().getTutorId();
        this.subject = session.getSubject_id().getSubject_id();
        this.sessionNotes = session.getSession_notes();
        this.childId = session.getChild_id().getChild_id();
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
}
