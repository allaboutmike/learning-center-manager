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

    //Find by childId
    //Create session
    //Update notes
    //Change tutor
    //Delete session
}
