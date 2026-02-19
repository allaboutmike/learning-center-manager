package com.learningcenter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

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
