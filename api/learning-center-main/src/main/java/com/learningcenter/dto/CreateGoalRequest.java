package com.learningcenter.dto;

public record CreateGoalRequest(
        Long subjectId,
        String title
) {
}