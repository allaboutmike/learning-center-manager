package com.learningcenter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CreateSessionRequest {
    /*
    CreateSessionRequest Object
    Handles the creation of new sessions.
    */

    private final Long tutorId;
    private final Long subjectId;
    private final String sessionNotes;
    private final Long childId;
    private final Long tutorTimeSlotId;


    public CreateSessionRequest(Long sessionId, Long tutorId, Long childId, Long subjectId, String sessionNotes, Long tutorTimeSlotId) {
        this.tutorId = tutorId;
        this.subjectId = subjectId;
        this.sessionNotes = sessionNotes;
        this.childId = childId;
        this.tutorTimeSlotId = tutorTimeSlotId;
    }

    public String getSessionNotes() {
        return sessionNotes;
    }

    public void setSessionNotes(String sessionNotes) {
        // Need logic here
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public Long getChildId() {
        return childId;
    }

    public Long getTutorTimeSlotId() {return tutorTimeSlotId;}

}
