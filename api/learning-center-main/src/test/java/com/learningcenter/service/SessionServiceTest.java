package com.learningcenter.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;

import com.learningcenter.dto.CreateSessionRequest;
import com.learningcenter.dto.SessionResponse;
import com.learningcenter.entities.Child;
import com.learningcenter.entities.Session;
import com.learningcenter.entities.Subject;
import com.learningcenter.entities.Timeslot;
import com.learningcenter.entities.Tutor;
import com.learningcenter.entities.TutorTimeslot;
import com.learningcenter.entities.Parent;
import com.learningcenter.repository.ChildRepository;
import com.learningcenter.repository.ParentRepository;
import com.learningcenter.repository.SessionRepository;
import com.learningcenter.repository.SubjectRepository;
import com.learningcenter.repository.TutorTimeSlotRepository;


public class SessionServiceTest {
    private SessionRepository sessionRepository;
    private ChildRepository childRepository;
    private TutorTimeSlotRepository tutorTimeSlotRepository;
    private SubjectRepository subjectRepository;
    private SessionService sessionService;
    private ParentRepository parentRepository;


    @BeforeEach
    void setUp() {
        sessionRepository = mock(SessionRepository.class);
        childRepository = mock(ChildRepository.class);
        tutorTimeSlotRepository = mock(TutorTimeSlotRepository.class);
        subjectRepository = mock(SubjectRepository.class);
        parentRepository = mock(ParentRepository.class);
        

        sessionService = new SessionService(sessionRepository, childRepository, tutorTimeSlotRepository, subjectRepository, parentRepository);
    }

    @Test
    void getUpComingSessions_returnsOnlyFuture() {
        Long parentId = 1L;
        Long childId = 2L;
        var future = sessionWithTime(10L, LocalDateTime.now().plusHours(1));
        Session past = sessionWithTime(11L, LocalDateTime.now().minusHours(1));

        when(sessionRepository.findSessionsByParentIdAndChildId(parentId, childId))
                .thenReturn(List.of(future, past));

        List<SessionResponse> result = sessionService.getUpcomingSessions(parentId, childId);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getSessionId());
        verify(sessionRepository).findSessionsByParentIdAndChildId(parentId, childId);
    }

    @Test
    void getPastSessions_returnsOnlyPast() {
        Long parentId = 1L;
        Long childId = 2L;
        var future = sessionWithTime(10L, LocalDateTime.now().plusHours(1));
        Session past = sessionWithTime(11L, LocalDateTime.now().minusHours(1));

        when(sessionRepository.findSessionsByParentIdAndChildId(parentId, childId))
                .thenReturn(List.of(future, past));

        List<SessionResponse> result = sessionService.getPastSessions(parentId, childId);

        assertEquals(1, result.size());
        assertEquals(11L, result.get(0).getSessionId());
        verify(sessionRepository).findSessionsByParentIdAndChildId(parentId, childId);
    }

    private Session sessionWithTime(Long sessionId, LocalDateTime time) {
        Session session = mock(Session.class);
        when(session.getSessionId()).thenReturn(sessionId);

        Subject subject = mock(Subject.class);
        when(subject.getSubjectId()).thenReturn(5L);
        when(session.getSubject()).thenReturn(subject);

        Child child = mock(Child.class);
        when(child.getChildId()).thenReturn(3L);
        when(session.getChild()).thenReturn(child);

        Tutor tutor = mock(Tutor.class);
        when(tutor.getTutorId()).thenReturn(100L);

        Timeslot timeslot = mock(Timeslot.class);
        when(timeslot.getTime()).thenReturn(time);

        TutorTimeslot tutorTimeSlot = mock(TutorTimeslot.class);
        when(tutorTimeSlot.getTimeslot()).thenReturn(timeslot);

        when(tutorTimeSlot.getTutor()).thenReturn(tutor);
        when(session.getTimeslot()).thenReturn(tutorTimeSlot);
        return session;
    }

    @Test
    void createSession_decreasesParentCredits() {
        // Arrange
        Long childId = 99L;
        Long tutorTimeSlotId = 99L;
        Long subjectId = 99L;
        Long parentId = 99L;
        int initialCredits = 10;

        // Create request
        CreateSessionRequest request = mock(CreateSessionRequest.class);
        when(request.childId()).thenReturn(childId);
        when(request.tutorTimeSlotId()).thenReturn(tutorTimeSlotId);
        when(request.subjectId()).thenReturn(subjectId);

        // Create mock entities
        Child child = mock(Child.class);
        Parent parent = mock(Parent.class);
        when(parent.getParentId()).thenReturn(parentId);
        when(parent.getCredits()).thenReturn(initialCredits);
        when(child.getParent()).thenReturn(parent);

        Subject subject = mock(Subject.class);
        Timeslot timeslot = mock(Timeslot.class);
        TutorTimeslot tutorTimeslot = mock(TutorTimeslot.class);
        when(tutorTimeslot.getTimeslot()).thenReturn(timeslot);

        // Setup mocks to return Optional values
        when(childRepository.findById(childId)).thenReturn(Optional.of(child));
        when(tutorTimeSlotRepository.findById(tutorTimeSlotId)).thenReturn(Optional.of(tutorTimeslot));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(parentRepository.findById(parentId)).thenReturn(Optional.of(parent));

        Session mockSession = mock(Session.class);
        when(sessionRepository.save(any(Session.class))).thenReturn(mockSession);

        // Act
        sessionService.createSession(request);

        // Assert - verify credits were decreased by 1
        verify(parent).setCredits(initialCredits - 1);
        verify(parentRepository).save(parent);
        verify(sessionRepository).save(any(Session.class));
    }

    //Test for when parent has insufficient credits
    @Test
    void createSession_insufficientCredits() {
        // Arrange
        Long childId = 99L;
        Long tutorTimeSlotId = 99L;
        Long subjectId = 99L;
        Long parentId = 99L;
        int initialCredits = 0;

        // Create request
        CreateSessionRequest request = mock(CreateSessionRequest.class);
        when(request.childId()).thenReturn(childId);
        when(request.tutorTimeSlotId()).thenReturn(tutorTimeSlotId);
        when(request.subjectId()).thenReturn(subjectId);

        // Create mock entities
        Child child = mock(Child.class);
        Parent parent = mock(Parent.class);
        when(parent.getParentId()).thenReturn(parentId);
        when(parent.getCredits()).thenReturn(initialCredits);
        when(child.getParent()).thenReturn(parent);

        Subject subject = mock(Subject.class);
        Timeslot timeslot = mock(Timeslot.class);
        TutorTimeslot tutorTimeslot = mock(TutorTimeslot.class);
        when(tutorTimeslot.getTimeslot()).thenReturn(timeslot);

        // Setup mocks to return Optional values
        when(childRepository.findById(childId)).thenReturn(Optional.of(child));
        when(tutorTimeSlotRepository.findById(tutorTimeSlotId)).thenReturn(Optional.of(tutorTimeslot));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(parentRepository.findById(parentId)).thenReturn(Optional.of(parent));

        // Act & Assert - expect an exception due to insufficient credits
        try {
            sessionService.createSession(request);
            assert false; // Fail the test if no exception is thrown
        } catch (Exception e) {
            assert e.getMessage().contains("Insufficient credits");
            verify(parent, never()).setCredits(anyInt());
            verify(parentRepository, never()).save(any(Parent.class));
            verify(sessionRepository, never()).save(any(Session.class));
        }
    }
}