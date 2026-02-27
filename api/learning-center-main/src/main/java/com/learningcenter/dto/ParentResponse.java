package com.learningcenter.dto;

import java.util.List;

public record ParentResponse(
    Long parentId,
    List<ChildResponse> children,
    List<SessionResponse> childSessions,
    Integer credits
){}