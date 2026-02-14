package com.learningcenter.dto;

import java.time.LocalDateTime;

public record TutorTimeSlotResponse(
        Long tutorTimeslotId,
        Long tutorId,
        Long timeslotId,
        LocalDateTime start,
        LocalDateTime end

) {
}
