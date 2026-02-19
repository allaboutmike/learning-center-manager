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
    private ChildRepository childRepository;
    private TutorTimeSlotRepository tutorTimeSlotRepository;
    private SubjectRepository subjectRepository;
    private SessionService sessionService;


    @BeforeEach
    void setUp() {
        sessionRepository = mock(SessionRepository.class);
        childRepository = mock(ChildRepository.class);
        tutorTimeSlotRepository = mock(TutorTimeSlotRepository.class);
        subjectRepository = mock(SubjectRepository.class);

        sessionService = new SessionService(sessionRepository, childRepository, tutorTimeSlotRepository, subjectRepository);
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

}





