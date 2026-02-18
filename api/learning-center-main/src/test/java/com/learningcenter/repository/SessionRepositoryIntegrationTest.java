package com.learningcenter.repository;

import com.learningcenter.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class SessionRepositoryIntegrationTest {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    TestEntityManager entityManager;

    //Test to find sessions by childId
    @Test
    public void findSessionsByChildId() {
        //Set time session was created
        LocalDateTime createdAt = LocalDateTime.now();
        //Create a parent
        Parent parent = new Parent("Trixie Mattel");
        //Create a child for the parent
        Child child = new Child("Tracy Martel", 6, parent);
        //Create a tutor
        Tutor tutor = new Tutor("Nikhil Patel", 2, 8, "www.example.com", "This is a test");
        //Create a timeslot for the session
        Timeslot timeslot = new Timeslot(LocalDateTime.of(2026, 2, 20, 15, 30));
        //Assign the timeslot to the tutor
        TutorTimeslot tutorTimeslot = new TutorTimeslot(tutor, timeslot);
        //Create a subject for the session
        Subject subject = new Subject("Math");
        //Create a new session using the previous data
        Session newSession = new Session("", createdAt, child, tutorTimeslot, subject);

        entityManager.persist(parent);
        entityManager.persist(child);
        entityManager.persist(tutor);
        entityManager.persist(timeslot);
        entityManager.persist(tutorTimeslot);
        entityManager.persist(subject);
        entityManager.persist(newSession);

        List<Session> sessions = sessionRepository.findSessionsByChildId(child.getChildId());
        assertThat(sessions.size()).isEqualTo(1);
    }
    //Test to create a new session
    @Test
    public void CreateNewSessionAndSaveThenSuccess() {
        //Set time session was created
        LocalDateTime createdAt = LocalDateTime.now();
        //Create a parent
        Parent parent = new Parent("Trixie Mattel");
        //Create a child for the parent
        Child child = new Child("Tracy Martel", 6, parent);
        //Create a tutor
        Tutor tutor = new Tutor("Nikhil Patel", 2, 8, "www.example.com", "This is a test");
        //Create a timeslot for the session
        Timeslot timeslot = new Timeslot(LocalDateTime.of(2026, 2, 20, 15, 30));
        //Assign the timeslot to the tutor
        TutorTimeslot tutorTimeslot = new TutorTimeslot(tutor, timeslot);
        //Create a subject for the session
        Subject subject = new Subject("Math");
        //Create a new session using the previous data
        Session newSession = new Session("", createdAt, child, tutorTimeslot, subject);

        entityManager.persist(parent);
        entityManager.persist(child);
        entityManager.persist(tutor);
        entityManager.persist(timeslot);
        entityManager.persist(tutorTimeslot);
        entityManager.persist(subject);
        entityManager.persist(newSession);

        sessionRepository.save(newSession);
        assertThat(entityManager.find(Session.class, newSession.getSessionId()).getSessionId()).isEqualTo(newSession.getSessionId());
    }
    //Test to update notes in a session
    //Test to change tutor assigned to a session
}
