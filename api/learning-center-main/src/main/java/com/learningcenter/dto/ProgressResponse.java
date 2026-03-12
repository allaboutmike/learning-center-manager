package com.learningcenter.dto;

import com.learningcenter.entities.Progress;

public record ProgressResponse(
        Long progressId,
        Integer percentageComplete
) {
    public ProgressResponse(Progress progress) {
        this(progress.getProgressId(), progress.getPercentageComplete());
    }
}