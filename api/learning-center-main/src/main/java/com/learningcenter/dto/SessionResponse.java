package com.learningcenter.dto;

import java.time.LocalDateTime;

import com.learningcenter.entities.Session;

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
    private Long parentId;
    private LocalDateTime time;
    private String childName;
    private String tutorName;
    private String subjectName;
    private Boolean attended;

    public SessionResponse(Session session) {
        this.sessionId = session.getSessionId();
        this.tutorId = session.getTimeslot().getTutor().getTutorId();
        this.subjectId = session.getSubject().getSubjectId();
        this.sessionNotes = session.getSessionNotes();
        this.childId = session.getChild().getChildId();
        this.time = session.getTimeslot().getTimeslot().getTime();
        this.childName = session.getChild().getName();
        this.tutorName = session.getTimeslot().getTutor().getName();
        this.subjectName = session.getSubject().getName();
        this.attended = session.getAttended();
        this.parentId = session.getChild().getParent().getParentId();
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getChildName() {
        return childName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public Long getChildId() {
        return childId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getSubjectName(){
        return subjectName;
    }

    public String getSessionNotes() {
        return sessionNotes;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Boolean getAttended() {
        return attended;
    }
}
