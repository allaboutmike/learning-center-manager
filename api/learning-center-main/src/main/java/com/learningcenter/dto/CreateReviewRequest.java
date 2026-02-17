package com.learningcenter.dto;

import com.learningcenter.entities.Tutor;

import java.time.LocalDateTime;

public record CreateReviewRequest (
    Long tutorId,
    String comment,
    Integer rating,
    LocalDateTime createdAt,
    Tutor tutor
) {
}
