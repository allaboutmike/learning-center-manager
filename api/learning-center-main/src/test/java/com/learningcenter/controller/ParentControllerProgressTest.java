package com.learningcenter.controller;

import com.learningcenter.dto.ChildProgressDashboardResponse;
import com.learningcenter.service.ChildProgressDashboardService;
import com.learningcenter.service.ParentService;
import com.learningcenter.service.SessionService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class ParentControllerProgressUnitTest {

    @Test
    void getChildProgressDashboard_demoTrue_callsMockDashboard() {
        ParentService parentService = mock(ParentService.class);
        SessionService sessionService = mock(SessionService.class);
        ChildProgressDashboardService dashboardService = mock(ChildProgressDashboardService.class);

        ParentController controller = new ParentController(parentService, sessionService, dashboardService);

        long parentId = 1L;
        long childId = 10L;
        String groupBy = "week";
        boolean demo = true;

        ChildProgressDashboardResponse expected = new ChildProgressDashboardResponse(
                childId,
                6,
                LocalDateTime.now(),
                List.of(),
                List.of(),
                List.of(),
                List.of()
        );

        when(dashboardService.getMockDashboard(childId, groupBy)).thenReturn(expected);

        ChildProgressDashboardResponse actual =
                controller.getChildProgressDashboard(parentId, childId, groupBy, demo);

        assertSame(expected, actual);

        verify(dashboardService).getMockDashboard(childId, groupBy);
        verify(dashboardService, never()).getChildProgressDashboard(anyLong(), anyLong(), anyString());
        verifyNoInteractions(parentService, sessionService);
    }

    @Test
    void getChildProgressDashboard_demoFalse_callsRealDashboard() {
        ParentService parentService = mock(ParentService.class);
        SessionService sessionService = mock(SessionService.class);
        ChildProgressDashboardService dashboardService = mock(ChildProgressDashboardService.class);

        ParentController controller = new ParentController(parentService, sessionService, dashboardService);

        long parentId = 1L;
        long childId = 10L;
        String groupBy = "month";
        boolean demo = false;

        ChildProgressDashboardResponse expected = new ChildProgressDashboardResponse(
                childId,
                2,
                LocalDateTime.now().minusDays(1),
                List.of(),
                List.of(),
                List.of(),
                List.of()
        );

        when(dashboardService.getChildProgressDashboard(parentId, childId, groupBy)).thenReturn(expected);

        ChildProgressDashboardResponse actual =
                controller.getChildProgressDashboard(parentId, childId, groupBy, demo);

        assertSame(expected, actual);

        verify(dashboardService).getChildProgressDashboard(parentId, childId, groupBy);
        verify(dashboardService, never()).getMockDashboard(anyLong(), anyString());
        verifyNoInteractions(parentService, sessionService);
    }
}