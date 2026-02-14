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

//    private final SessionService sessionService;
//
//    public SessionController(SessionService sessionService) {
//        this.sessionService = sessionService;
//    }

/*
SessionController API Documentation

-----Overview-----
This controller manages tutoring session scheduling between students and tutors. This controller allows:
    * Creating a new tutoring session
    * Retrieving session details

Authentication may be needed later on, which would make all endpoints require authenticated users. It would rule:
    * Tutors cannot create sessions on behalf of the students or parent.
    * Only the parent or tutor assigned to a session may retrieve its details

-----Endpoints-----
1. Create Session
   Endpoint: POST /api/sessions
   Description: Creates a new tutoring session between a student and tutor.

    Request Body:
    {
      "studentId": 5,
      "tutorId": 11,
      "startTime": "2025-01-15T14:00:00",
      "endTime": "2025-01-15T15:00:00"
    }

    Expected Response:
    Status: 201 Created
    Body: Session object with generated session ID and confirmation details

    Path Parameters:
    * studentId (required, Long) - id of the student the parent booked for the session
    * tutorId (required, Long) - id of the tutor assigned
    * startTime (required, datetime) - Session start time
    * endTime (required, datetime) - Session end time

    Possible Error Responses with descriptions:
    * 400 Bad Request - invalid input data
    * 401 Unauthorized - User not authenticated
    * 403 Forbidden - User not authorized
    * 404 Not Found - Student or Tutor not found
    * 409 Conflict - Time slot already booked

    Example Error Response:
    {
      "timestamp": "2025-01-10T09:15:10",
      "status": 409,
      "error": "Conflict",
      "message": "Tutor already has a session scheduled during this time.",
      "path": "/api/sessions"
     }

2. Retrieve Session Details
    Endpoint: GET /api/sessions/{sessionId}
    Description: Retrieves detailed information and confirmation details for a specific session.

    Path Parameters:
    * sessionId (required, integer): The unique identifier of the session

    Example Request:
    GET /api/sessions/111
    Expected Response: Status: 200 OK
    Body: Session object with complete details including student, tutor, and time information

    Expected json response:
    {
      "id": 111,
      "student": {
        "id": 5,
        "name": John Doe,
    },
      "tutor": {
         "id": 11,
         "name": "Jane Smith"
         "subject": "Mathematics"
    },
    "startTime": "2026-01-15T14:00:00",
    "endTime": "2026-01-15T15:00:00",
    "status": "CONFIRMED"
   }

   Possible error responses with description:
   * 401 Unauthorized - User not authenticated
   * 403 Forbidden - User not assigned to session
   * 404 Not Found - Session does not exist
*/

    // Handles POST requests to /api/sessions
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateSessionRequest createSession(@RequestBody CreateSessionRequest request) {

        // Return the created object and a 201 Created status
//        return sessionService.createSession(request);
        return request;
    }


    // Handles GET request for session details by sessionId
    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable Long sessionId) {
//        SessionResponse session = sessionService.getSessionById(sessionId);

        // Return the session details and a 200 success status
//        return ResponseEntity.ok(session);
        return null;
    }
}
