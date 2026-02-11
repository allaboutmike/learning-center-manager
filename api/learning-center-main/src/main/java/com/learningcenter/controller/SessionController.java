package com.learningcenter.controller;


import com.learningcenter.dto.CreateSessionRequest;
import com.learningcenter.dto.SessionResponse;
import com.learningcenter.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }
/*
SessionController API Documentation
REST API controller for managing tutoring session operations in the Learning Center application.

-----Endpoints-----
1. Create Session
    POST /api/sessions
    Creates a new tutoring session between a student and tutor.

    Request Body:
    {
      "studentId": 5,
      "tutorId": 11,
      "startTime": "2025-01-15T14:00:00",
      "endTime": "2025-01-15T15:00:00"
    }

    Expected Response: Status: 201 Created
    Body: Session object with generated session ID and confirmation details

2. Retrieve Session Details
    GET /api/sessions/{sessionId}
    Retrieves detailed information and confirmation details for a specific session.

    Path Parameters: sessionId (required, integer): The unique identifier of the session
    Example Request: GET /api/sessions/111

    Expected Response: Status: 200 OK
    Body: Session object with complete details including student, tutor, and time information
*/

    // Handles POST requests to /api/sessions
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateSessionRequest createSession(@RequestBody CreateSessionRequest request) {

        // Return the created object and a 201 Created status
        return sessionService.createSession(request);
    }


    // Handles GET request for session details by sessionId
    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable Long sessionId) {
        SessionResponse session = sessionService.getSessionById(sessionId);

        // Return the session details and a 200 success status
        return ResponseEntity.ok(session);
    }
}
