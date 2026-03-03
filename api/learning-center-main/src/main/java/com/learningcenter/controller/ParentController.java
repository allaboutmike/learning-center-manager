package com.learningcenter.controller;

import com.learningcenter.dto.ChildProgressDashboardResponse;
import com.learningcenter.dto.ChildResponse;
import com.learningcenter.dto.SessionResponse;
import com.learningcenter.service.ChildProgressDashboardService;
import com.learningcenter.service.ParentService;
import com.learningcenter.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {
    private final ParentService parentService;
    private final SessionService sessionService;
    private final ChildProgressDashboardService childProgressDashboardService;

    public ParentController(ParentService parentService, SessionService sessionService, ChildProgressDashboardService childProgressDashboardService) {
        this.parentService = parentService;
        this.sessionService = sessionService;
        this.childProgressDashboardService = childProgressDashboardService;
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
        return sessionService.getUpcomingSessions(parentId, childId);
    }

    //Handles GET request to get credit balance
    @GetMapping("/{parentId}/creditBalance")
    public Integer getCreditsByParentId(@PathVariable(required = true) Long parentId){
        return parentService.getCreditsByParentId(parentId);
    }


    //Handles PUT request to increase credit balance by amount purchased
    @PutMapping("/{parentId}/creditBalance/purchasedCredits")
    @ResponseStatus(HttpStatus.OK)
    public Integer addCreditsByParentId(@PathVariable(required = true) Long parentId, Integer credits) {
       return parentService.addCreditsByParentId(parentId, credits);
    }
    @GetMapping("/{parentId}/children/{childId}/progress")
    public ChildProgressDashboardResponse getChildProgressDashboard(
            @PathVariable Long parentId,
            @PathVariable Long childId,
            @RequestParam(required = false, defaultValue = "week") String groupBy,
            @RequestParam(required = false, defaultValue = "false") boolean demo
    ) {
        if (demo) {
            return childProgressDashboardService.getMockDashboard(childId, groupBy);
        }

        return childProgressDashboardService.getChildProgressDashboard(parentId, childId, groupBy);
    }
}

