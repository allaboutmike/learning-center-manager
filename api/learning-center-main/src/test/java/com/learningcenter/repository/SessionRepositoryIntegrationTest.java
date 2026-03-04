package com.learningcenter.repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;
import com.learningcenter.entities.Session;
import com.learningcenter.entities.Subject;
import com.learningcenter.entities.Timeslot;
import com.learningcenter.entities.Tutor;
import com.learningcenter.entities.TutorTimeslot;

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
        Parent parent = new Parent("Trixie Mattel", 15);
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
        Parent parent = new Parent("Kai Wachi", 10);
        //Create a child for the parent
        Child child = new Child("Sullivan King", 6, parent);
        //Create a tutor
        Tutor tutor = new Tutor("Ray Volpe", 2, 8, "www.example.com", "This is a test");
        //Create a timeslot for the session
        Timeslot timeslot = new Timeslot(LocalDateTime.of(2026, 2, 20, 15, 30));
        //Assign the timeslot to the tutor
        TutorTimeslot tutorTimeslot = new TutorTimeslot(tutor, timeslot);
        //Create a subject for the session
        Subject subject = new Subject("Science");
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
    @Test
    public void CreateNewSessionAndUpdateNotesThenSuccess() {
        //Set time session was created
        LocalDateTime createdAt = LocalDateTime.now();
        //Create a parent
        Parent parent = new Parent("Sara Landry", 15);
        //Create a child for the parent
        Child child = new Child("Charlotte de Witte", 6, parent);
        //Create a tutor
        Tutor tutor = new Tutor("Nico Moreno", 2, 8, "www.example.com", "This is a test");
        //Create a timeslot for the session
        Timeslot timeslot = new Timeslot(LocalDateTime.of(2026, 2, 20, 15, 30));
        //Assign the timeslot to the tutor
        TutorTimeslot tutorTimeslot = new TutorTimeslot(tutor, timeslot);
        //Create a subject for the session
        Subject subject = new Subject("Science");
        //Create a new session using the previous data
        Session newSession = new Session("", createdAt, child, tutorTimeslot, subject);

        entityManager.persist(parent);
        entityManager.persist(child);
        entityManager.persist(tutor);
        entityManager.persist(timeslot);
        entityManager.persist(tutorTimeslot);
        entityManager.persist(subject);
        entityManager.persist(newSession);

        newSession.setSessionNotes("I changed the notes");
        assertThat(newSession.getSessionNotes()).isEqualTo("I changed the notes");
    }

    //Testing null for session notes
    public void CreateSessionWithNullSessionNotesThenSuccess() {
        //Set time session was created
        LocalDateTime createdAt = LocalDateTime.now();
        //Create a parent
        Parent parent = new Parent("Sara Landry", 15);
        //Create a child for the parent
        Child child = new Child("Charlotte de Witte", 6, parent);
        //Create a tutor
        Tutor tutor = new Tutor("Nico Moreno", 2, 8, "www.example.com", "This is a test");
        //Create a timeslot for the session
        Timeslot timeslot = new Timeslot(LocalDateTime.of(2026, 2, 20, 15, 30));
        //Assign the timeslot to the tutor
        TutorTimeslot tutorTimeslot = new TutorTimeslot(tutor, timeslot);
        //Create a subject for the session
        Subject subject = new Subject("Science");
        //Create a new session using the previous data
        Session newSession = new Session(null, createdAt, child, tutorTimeslot, subject);

        entityManager.persist(parent);
        entityManager.persist(child);
        entityManager.persist(tutor);
        entityManager.persist(timeslot);
        entityManager.persist(tutorTimeslot);
        entityManager.persist(subject);
        entityManager.persist(newSession);

        assertThat(newSession.getSessionNotes()).isEqualTo(null);
    }
}
