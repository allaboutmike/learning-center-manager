package com.learningcenter.controller;

import com.learningcenter.dto.ChildResponse;
import com.learningcenter.dto.SessionResponse;
import com.learningcenter.service.ParentService;
import com.learningcenter.service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {
    private final ParentService parentService;
    private final SessionService sessionService;

    public ParentController(ParentService parentService, SessionService sessionService) {
        this.parentService = parentService;
        this.sessionService = sessionService;
    }

    //Handles GET request to get children associated to a parent by the parentId
    @GetMapping("/{parentId}/children")
    public List<ChildResponse> searchChildrenByParentId(@PathVariable Long parentId) {
        return parentService.getChildrenByParent(parentId);
    }

    //Handles GET request to get a child's past sessions
    @GetMapping("/{parentId}/children/{childId}/sessions/past")
    public List<SessionResponse> getPastSessionsByParentIdAndChildId(@PathVariable(required = true) Long parentId, @PathVariable(required = true) Long childId) {

        return sessionService.getPastSessions(parentId, childId);
    }

    //Handles GET request to get a child's upcoming sessions
    @GetMapping("/{parentId}/children/{childId}/sessions/upcoming")
    public List<SessionResponse> getUpcomingSessionsByParentIdAndChildId(@PathVariable(required = true) Long parentId, @PathVariable(required = true) Long childId) {
        var upcomingSessions = sessionService.getUpcomingSessions(parentId, childId);

        return upcomingSessions;
    }
}
