package com.learningcenter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ChildProgressDashboardResponse(
        Long childId,
        Integer totalCompletedSessions,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime mostRecentSessionDate,

        List<ChartPoint> sessionsOverTime,
        List<ChartPoint> subjectBreakdown,
        List<ChartPoint> currentSubjects,
        List<ChartPoint> lastTutorNotes,

        List<GoalProgress> goals
) {

    public record ChartPoint(
            String label,
            Integer value,
            LocalDateTime date,
            String extra
    ) {}

    public record GoalProgress(
            Long goalId,
            String subject,
            String title,
            Integer percentageComplete
    ) {}
}