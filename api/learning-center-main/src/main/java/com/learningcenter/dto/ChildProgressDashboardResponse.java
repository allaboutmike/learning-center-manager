package com.learningcenter.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ChildProgressDashboardResponse(
        Long childId,
        Integer totalCompletedSessions,
        LocalDateTime mostRecentSessionDate,
        List<ChartPoint> sessionsOverTime,
        List<ChartPoint> subjectBreakdown,
        List<ChartPoint> currentSubjects,
        List<ChartPoint> lastTutorNotes
) {
    public record ChartPoint(
            String label,
            Integer value,
            LocalDateTime date,
            String extra
    ) {}
}