package com.learningcenter.controller;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.learningcenter.dto.*;
import com.learningcenter.entities.Session;
import com.learningcenter.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class ParentControllerTest {

    @Autowired
    private ParentController parentController;

    @Autowired
    private SessionRepository sessionRepository;

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
        Long expectedCount = sessionRepository.findSessionsByParentIdAndChildId(1L, 1L).stream().filter((Session s) -> s.getTimeslot().getTimeslot().getTime().isBefore(LocalDateTime.now())).count();

        List<SessionResponse> pastSessions = parentController.getPastSessionsByParentIdAndChildId(1L, 1L);
        assertNotNull(pastSessions);
        assertEquals(expectedCount, pastSessions.size());
    }

    @Test
    void searchUpcomingSessionsByChildIdAndParentIdAndReturnSessionsList() {
        Long expectedCount = sessionRepository.findSessionsByParentIdAndChildId(2L, 2L).stream().filter((Session s) -> s.getTimeslot().getTimeslot().getTime().isAfter(LocalDateTime.now())).count();

        List<SessionResponse> upcomingSessions = parentController.getUpcomingSessionsByParentIdAndChildId(2L, 2L);
        assertNotNull(upcomingSessions);
        assertEquals(expectedCount, upcomingSessions.size());
    }

    @Test
    void createParent_returnsCreatedParent() {
        CreateParentRequest request = new CreateParentRequest("Test Parent", "test.create@example.com", null);

        ParentResponse result = parentController.createParent(request);

        assertNotNull(result);
        assertNotNull(result.parentId());
        assertEquals("test.create@example.com", result.email());
        assertEquals(0, result.credits());
    }

    @Test
    void createParent_duplicateEmail_throwsConflict() {
        CreateParentRequest first = new CreateParentRequest("First", "conflict@example.com", null);
        parentController.createParent(first);

        CreateParentRequest second = new CreateParentRequest("Second", "conflict@example.com", null);

        assertThrows(ResponseStatusException.class, () -> parentController.createParent(second));
    }

    @Test
    void createChild_returnsCreatedChild() {
        CreateChildRequest request = new CreateChildRequest("Test Child", 3);

        ChildResponse result = parentController.createChild(1L, request);

        assertNotNull(result);
        assertNotNull(result.childId());
        assertEquals("Test Child", result.firstName());
        assertEquals(3, result.gradeLevel());
    }
}
