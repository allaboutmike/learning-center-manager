package com.learningcenter.service;

import com.learningcenter.dto.ChildProgressDashboardResponse;
import com.learningcenter.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChildProgressDashboardServiceNoMockTest {

    private SessionRepository repo;
    private ChildProgressDashboardService service;

    @BeforeEach
    void setUp() {
        repo = mock(SessionRepository.class);
        service = new ChildProgressDashboardService(repo);
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

        verifyNoInteractions(repo);
    }

    @Test
    void getChildProgressDashboard_invalidGroupBy_throws400() {
        ErrorResponseException ex = assertThrows(
                ErrorResponseException.class,
                () -> service.getChildProgressDashboard(1L, 10L, "year")
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertNotNull(ex.getBody());
        assertTrue(ex.getBody().getDetail().contains("groupBy"));

        verifyNoInteractions(repo);
    }

    @Test
    void getChildProgressDashboard_validRequest_withNoSessions_returnsEmptyCollections() {
        when(repo.findDashboardSessionsByParentIdAndChildId(1L, 10L)).thenReturn(List.of());

        ChildProgressDashboardResponse resp =
                service.getChildProgressDashboard(1L, 10L, "week");

        assertEquals(10L, resp.childId());
        assertEquals(0, resp.totalCompletedSessions());
        assertNull(resp.mostRecentSessionDate());

        assertNotNull(resp.sessionsOverTime());
        assertNotNull(resp.subjectBreakdown());
        assertNotNull(resp.currentSubjects());
        assertNotNull(resp.lastTutorNotes());

        assertTrue(resp.sessionsOverTime().isEmpty());
        assertTrue(resp.subjectBreakdown().isEmpty());
        assertTrue(resp.currentSubjects().isEmpty());
        assertTrue(resp.lastTutorNotes().isEmpty());

        verify(repo).findDashboardSessionsByParentIdAndChildId(1L, 10L);
    }
}