package com.learningcenter.service;

import com.learningcenter.dto.CreateSessionRequest;
import com.learningcenter.dto.SessionResponse;
import com.learningcenter.entities.Child;
import com.learningcenter.entities.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionService {
/*
SessionService API Documentation
    Service class for managing session-related operations in the Learning Center application.
    Handles business logic for scheduling, retrieving, and managing tutoring sessions.

-----Methods-----
 1. Create Session
    Creates a new tutoring session between a student and tutor.

    Method Signature: public Session createSession(int studentId, int tutorId, LocalDateTime startTime, LocalDateTime endTime)

    Parameters:
    studentId (int): The unique identifier of the student
    tutorId (int): The unique identifier of the tutor
    startTime (LocalDateTime): Session start time
    endTime (LocalDateTime): Session end time

    Returns: Session object with generated session ID
    Throws: SessionException if tutor is unavailable or session conflicts exist

2. Get Session by ID
    Retrieves detailed information about a specific session.

    Method Signature: public Session getSessionById(int sessionId)

    Parameters:
    sessionId (int): The unique identifier of the session

    Returns: Session object with complete details
    Throws: SessionNotFoundException if session does not exist

3. Get Sessions by Student
    Retrieves all sessions for a specific student.
    Method Signature: public List<Session> getSessionsByStudentId(int studentId)
    Parameters:
    studentId (int): The unique identifier of the student

    Returns: List of Session objects associated with the student

4. Cancel Session
    Cancels an existing session.
    Method Signature: public void cancelSession(int sessionId)
    Parameters:
    sessionId (int): The unique identifier of the session to cancel

    Throws: SessionNotFoundException if session does not exist
*/

    public CreateSessionRequest createSession(CreateSessionRequest request) {

        Session session = new Session(request.getChildId(), request.getSessionId(), request.getTutorId(), request.getSessionNotes(), request.getStartTime(), request.getEndTime(), request.getSubject());

        return request;
    };

    public SessionResponse getSessionById(Long sessionId) {
        SessionResponse response = new SessionResponse();
        response.setSessionId(sessionId);
        response.setStatus("Confirm");

        return response;
    };

    public List<Session> getSessionsByStudent(int studentId) {


        List<Session> studentSessions = new ArrayList<>();
        return studentSessions;
    };
}
