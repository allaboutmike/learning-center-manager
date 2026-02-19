package com.learningcenter.dto;

public class SubjectResponse {
    private Long subjectId;
    private String name;

    public SubjectResponse(Long subjectId, String name) {
        this.subjectId = subjectId;
        this.name = name;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getName() {
        return name;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
