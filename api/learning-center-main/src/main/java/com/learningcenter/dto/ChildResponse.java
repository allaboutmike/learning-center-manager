package com.learningcenter.dto;

public record ChildResponse(
        Long childId,
        String firstName,
        Integer gradeLevel
) {}
