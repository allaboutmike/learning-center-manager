package com.learningcenter.service;

import com.learningcenter.dto.SessionResponse;
import com.learningcenter.entities.*;
import com.learningcenter.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SessionServiceTest {
    private SessionRepository sessionRepository;
    private ParentRepository parentRepository;
    private SessionService sessionService;


    @BeforeEach
    void setUp() {
        sessionRepository = mock(SessionRepository.class);
        parentRepository = mock(ParentRepository.class);
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

    @Test
    void getChildrenByParent_returnsChildren() {
        Long parentId = 1L;
        Session session1 = sessionWithTime(10L, LocalDateTime.now());
        Session session2 = sessionWithTime(11L, LocalDateTime.now());

        Child child1 = session1.getChild();
        child1.setChildId(3L);
        child1.setName("Alice");

        Child child2 = session2.getChild();
        child2.setChildId(4L);
        child2.setName("Bob");

        when(parentRepository.listOfChildrenByParentId(parentId)).thenReturn(List.of(child1, child2));
        var result = sessionService.getChildrenByParent(parentId);

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).firstName());
        assertEquals("Bob", result.get(1).firstName());
        verify(parentRepository).listOfChildrenByParentId(parentId);


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

}





