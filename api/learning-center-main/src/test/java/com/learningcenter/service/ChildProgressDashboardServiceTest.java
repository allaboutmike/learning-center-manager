package com.learningcenter.service;

import com.learningcenter.dto.ChildProgressDashboardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChildProgressDashboardServiceNoMockTest {

    private ChildProgressDashboardService service;

    @BeforeEach
    void setUp() {
        service = new ChildProgressDashboardService(null);
    }

    @Test
    void getMockDashboard_week_returnsExpectedShape() {
        Long childId = 10L;

        ChildProgressDashboardResponse resp = service.getMockDashboard(childId, "week");

        assertEquals(childId, resp.childId());
        assertEquals(6, resp.totalCompletedSessions());
        assertNotNull(resp.mostRecentSessionDate());

        assertEquals(3, resp.sessionsOverTime().size());
        assertEquals("2026-W05", resp.sessionsOverTime().get(0).label());
        assertEquals(1, resp.sessionsOverTime().get(0).value());
        assertEquals("2026-W06", resp.sessionsOverTime().get(1).label());
        assertEquals(2, resp.sessionsOverTime().get(1).value());
        assertEquals("2026-W07", resp.sessionsOverTime().get(2).label());
        assertEquals(3, resp.sessionsOverTime().get(2).value());

        assertEquals(3, resp.subjectBreakdown().size());
        assertEquals(List.of("Math", "Science", "English"),
                resp.subjectBreakdown().stream().map(ChildProgressDashboardResponse.ChartPoint::label).toList());

        assertEquals(2, resp.currentSubjects().size());
        assertEquals(List.of("Math", "Science"),
                resp.currentSubjects().stream().map(ChildProgressDashboardResponse.ChartPoint::label).toList());

        assertEquals(3, resp.lastTutorNotes().size());
        assertTrue(resp.lastTutorNotes().get(0).extra().contains("Child " + childId));        assertNotNull(resp.lastTutorNotes().get(0).date());
    }

    @Test
    void getMockDashboard_month_returnsExpectedBuckets() {
        Long childId = 1L;

        ChildProgressDashboardResponse resp = service.getMockDashboard(childId, "month");

        assertEquals(3, resp.sessionsOverTime().size());
        assertEquals("2026-01", resp.sessionsOverTime().get(0).label());
        assertEquals(1, resp.sessionsOverTime().get(0).value());
        assertEquals("2026-02", resp.sessionsOverTime().get(1).label());
        assertEquals(3, resp.sessionsOverTime().get(1).value());
        assertEquals("2026-03", resp.sessionsOverTime().get(2).label());
        assertEquals(2, resp.sessionsOverTime().get(2).value());
    }

    @Test
    void getMockDashboard_invalidGroupBy_throws400() {
        ErrorResponseException ex = assertThrows(
                ErrorResponseException.class,
                () -> service.getMockDashboard(1L, "year")
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertNotNull(ex.getBody());
        assertTrue(ex.getBody().getDetail().contains("groupBy"));
    }

    @Test
    void getChildProgressDashboard_nullIds_throws400_beforeRepoIsUsed() {
        ErrorResponseException ex = assertThrows(
                ErrorResponseException.class,
                () -> service.getChildProgressDashboard(null, 10L, "week")
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertNotNull(ex.getBody());
        assertTrue(ex.getBody().getDetail().contains("parentId and childId are required"));
    }
}