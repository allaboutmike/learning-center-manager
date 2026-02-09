package com.learningcenter.service;

public class TutorService {
/*
TutorService API Documentation
    Service class for managing tutor-related operations in the Learning Center application.
    Handles business logic for tutor management, availability, and scheduling operations.

-----Methods-----
1. Get Tutor by ID
    Retrieves detailed information about a specific tutor.

    Method Signature: public Tutor getTutorById(int tutorId)

    Parameters:
    tutorId (int): The unique identifier of the tutor
    Returns: Tutor object with complete details
    Throws: TutorNotFoundException if tutor does not exist

2. Search Tutors by Grade Level
    Retrieves all tutors filtered by grade level.

    Method Signature: public List<Tutor> searchTutorsByGradeLevel(int gradeLevel)

    Parameters:
    gradeLevel (int): The grade level to filter tutors by
    Returns: List of Tutor objects matching the specified grade level

3. Get Tutor Availability
    Retrieves the availability schedule for a specific tutor.

    Method Signature: public TutorAvailability getTutorAvailability(int tutorId)

    Parameters:
    tutorId (int): The unique identifier of the tutor
    Returns: TutorAvailability object containing the tutor's schedule
    Throws: TutorNotFoundException if tutor does not exist

4. Update Tutor Availability
    Updates the availability schedule for a specific tutor.

    Method Signature: public void updateTutorAvailability(int tutorId, TutorAvailability availability)

    Parameters:
    tutorId (int): The unique identifier of the tutor
    availability (TutorAvailability): Updated availability schedule
    Throws: TutorNotFoundException if tutor does not exist
*/
}
