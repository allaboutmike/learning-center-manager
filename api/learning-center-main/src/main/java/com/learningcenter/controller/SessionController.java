package com.learningcenter.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
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


}
