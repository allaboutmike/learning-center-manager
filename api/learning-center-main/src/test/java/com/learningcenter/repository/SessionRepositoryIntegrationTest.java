package com.learningcenter.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

@DataJpaTest
public class SessionRepositoryIntegrationTest {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    TestEntityManager testEntityManager;

    //Test to find sessions by childId
    //Test to create a new session
    //Test to update notes in a session
    //Test to change tutor assigned to a session
    //Test to delete a session
}
