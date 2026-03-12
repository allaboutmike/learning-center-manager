package com.learningcenter.dto;

public record CreateProgressRequest(
        Long sessionId,
        Integer percentageComplete
) {}