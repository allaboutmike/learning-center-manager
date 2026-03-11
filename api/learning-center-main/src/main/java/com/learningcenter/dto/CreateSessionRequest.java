package com.learningcenter.dto;

public record CreateSessionRequest (
    /*
    CreateSessionRequest Object
    Handles the creation of new sessions.
    */

    Long tutorId,
    Long subjectId,
    String sessionNotes,
    Long childId,
    Long tutorTimeSlotId
) {
    }
