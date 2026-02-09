package com.learningcenter.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TutorController {
/*
TutorController API Documentation
REST API controller for managing tutor-related operations in the Learning Center application.

-----Endpoints-----
1. Search Tutors by Grade Level
    GET /api/tutors
    Searches and retrieves tutors filtered by grade level.

    Query Parameters:
    gradeLevel (required, integer): The grade level to filter tutors by
    Example Request: GET /api/tutors?gradeLevel=7

    Expected Response: Status: 200 OK
    Body: List of tutor objects matching the specified grade level

2. Retrieve Tutor Details
    GET /api/tutors/{tutorId}
    Retrieves detailed information about a specific tutor.

    Path Parameters: tutorId (required, integer): The unique identifier of the tutor
    Example Request: GET /api/tutors/11

    Expected Response: Status: 200 OK
    Body: Tutor object with complete details

3. Get Tutor Availability
    GET /api/tutors/{tutorId}/availability
    Retrieves the availability schedule for a specific tutor.

    Path Parameters: tutorId (required, integer): The unique identifier of the tutor
    Example Request: GET /api/tutors/11/availability
    Expected Response: Status: 200 OK
    Body: Tutor availability schedule/calendar
*/

}
