package com.learningcenter.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.learningcenter.dto.ChildResponse;
import com.learningcenter.dto.SessionResponse;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class ParentControllerTest {

    @Autowired
    private ParentController parentController;

    @Test
    void searchMultipleChildrenByParentIdAndReturnChildList() {
        List<ChildResponse> children = parentController.searchChildrenByParentId(2L);
        assertNotNull(children);
        assertEquals(3, children.size());
    }

    @Test
    void searchSingleChildByParentIdAndReturnChildList() {
        List<ChildResponse> children = parentController.searchChildrenByParentId(1L);
        assertNotNull(children);
        assertEquals(1, children.size());
    }

    @Test
    void searchChildrenByParentIdAndReturnEmptyChildList() {
        List<ChildResponse> children = parentController.searchChildrenByParentId(3L);
        assertNotNull(children);
        assertEquals(0, children.size());
    }

    @Test
    void searchPastSessionsByChildIdAndParentIdAndReturnSessionsList() {
        List<SessionResponse> pastSessions = parentController.getPastSessionsByParentIdAndChildId(1L, 1L);
        assertNotNull(pastSessions);
        assertEquals(2, pastSessions.size());
    }

    @Test
    void searchUpcomingSessionsByChildIdAndParentIdAndReturnSessionsList() {
        List<SessionResponse> upcomingSessions = parentController.getUpcomingSessionsByParentIdAndChildId(2L, 2L);
        assertNotNull(upcomingSessions);
        assertEquals(0, upcomingSessions.size());
    }
}
