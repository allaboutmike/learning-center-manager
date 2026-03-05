package com.learningcenter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateParentRequest(
    @NotBlank String name,
    @NotBlank @Email String email,
    String phone  // optional, may be null
) {}
