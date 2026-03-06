package com.learningcenter.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateChildRequest(

        @NotBlank(message = "Child name is required")
        String name,

        @NotNull(message = "Grade level is required")
        @Min(value = 0, message = "Grade level must be valid")
        @Max(value = 12, message = "Grade level must be valid")
        Integer gradeLevel
) {}