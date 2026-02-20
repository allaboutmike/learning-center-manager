package com.learningcenter.service;

import com.learningcenter.dto.ChildResponse;
import com.learningcenter.dto.CreateSessionRequest;
import com.learningcenter.dto.SessionResponse;
import com.learningcenter.entities.Child;
import com.learningcenter.entities.Session;
import com.learningcenter.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
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
    private SessionRepository sessionRepository;
    private ChildRepository childRepository;
    private TutorTimeSlotRepository tutorTimeSlotRepository;
    private SubjectRepository subjectRepository;

    public SessionService(SessionRepository sessionRepository, ChildRepository childRepository, TutorTimeSlotRepository tutorTimeSlotRepository, SubjectRepository subjectRepository) {
        this.sessionRepository = sessionRepository;
        this.childRepository = childRepository;
        this.tutorTimeSlotRepository = tutorTimeSlotRepository;
        this.subjectRepository = subjectRepository;
    }

    public Session createSession(CreateSessionRequest request) {
        /*
        Need Child repository, Timeslot, Subject
        * */
        var child = childRepository.findById(request.childId());
        var tutorTimeSlot = tutorTimeSlotRepository.findById(request.tutorTimeSlotId());
        var subject = subjectRepository.findById(request.subjectId());
        Session session = new Session("", LocalDateTime.now(), child.get(), tutorTimeSlot.get(), subject.get());
        return sessionRepository.save(session);
    }


    public Session getSessionById(Long sessionId) {

        return sessionRepository.findById(sessionId).get();
    }


    public List<Session> getSessionsByStudent(Long childId) {

        var studentSessions = sessionRepository.findSessionsByChildId(childId);
        return studentSessions;
    }



    public List<SessionResponse> getUpcomingSessions(Long parentId, Long childId) {
        var now = LocalDateTime.now();
        var sessions = sessionRepository.findSessionsByParentIdAndChildId(parentId, childId);
        var responseList = new ArrayList<SessionResponse>();
        for (var session : sessions) {
            var tutorTimeslot = session.getTimeslot();
            if (tutorTimeslot == null || tutorTimeslot.getTimeslot() == null) {
                continue;
            }
            var sessionTime = tutorTimeslot.getTimeslot().getTime();

            if (sessionTime != null && sessionTime.isAfter(now)) {
                responseList.add(new SessionResponse(session));
            }
        }
        return responseList;
    }

    public List<SessionResponse> getPastSessions(Long parentId, Long childId) {
        var now = LocalDateTime.now();
        var sessions = sessionRepository.findSessionsByParentIdAndChildId(parentId, childId);
        var responseList = new ArrayList<SessionResponse>();

        for (var session : sessions) {

            var tutorTimeslot = session.getTimeslot();
            if (tutorTimeslot == null || tutorTimeslot.getTimeslot() == null) {
                continue;
            }
            var sessionTime = tutorTimeslot.getTimeslot().getTime();
            if (sessionTime != null && sessionTime.isBefore(now)) {
                responseList.add(new SessionResponse(session));
            }
        }
        return responseList;

    }
}
