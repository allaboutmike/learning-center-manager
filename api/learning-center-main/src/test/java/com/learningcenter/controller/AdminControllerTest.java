package com.learningcenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.learningcenter.dto.AdminStatsResponse;
import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;
import com.learningcenter.repository.ChildRepository;
import com.learningcenter.repository.ParentRepository;
import com.learningcenter.repository.TutorRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class AdminControllerTest {

    @Autowired
    private AdminController adminController;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Test
    void getStats_returnsNonNullResponse() {
        AdminStatsResponse stats = adminController.getStats();
        assertNotNull(stats);
    }

    @Test
    void getStats_returnsCorrectParentCount() {
        long before = parentRepository.count();
        Parent alice = parentRepository.save(new Parent("Alice", "alice@test.com", null));
        alice.setCredits(10);
        parentRepository.save(alice);
        Parent bob = parentRepository.save(new Parent("Bob", "bob@test.com", null));
        bob.setCredits(20);
        parentRepository.save(bob);

        AdminStatsResponse stats = adminController.getStats();
        assertEquals(before + 2, stats.getParents());
    }

    @Test
    void getStats_returnsCorrectStudentCount() {
        long before = childRepository.count();
        Parent parent = parentRepository.save(new Parent("Carol", "carol@test.com", null));
        childRepository.save(new Child("Tom", 3, parent));

        AdminStatsResponse stats = adminController.getStats();
        assertEquals(before + 1, stats.getStudents());
    }

    @Test
    void getStats_returnsCorrectCreditsPurchased() {
        long creditsBefore = parentRepository.sumAllCredits();
        Parent dan = new Parent("Dan", "dan@test.com", null);
        dan.setCredits(50);
        parentRepository.save(dan);
        Parent eve = new Parent("Eve", "eve@test.com", null);
        eve.setCredits(30);
        parentRepository.save(eve);

        AdminStatsResponse stats = adminController.getStats();
        assertEquals(creditsBefore + 80, stats.getCreditsPurchased());
    }
}
