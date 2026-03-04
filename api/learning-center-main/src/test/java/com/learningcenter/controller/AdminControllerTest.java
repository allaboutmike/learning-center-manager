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
        parentRepository.save(new Parent("Alice", 10));
        parentRepository.save(new Parent("Bob", 20));

        AdminStatsResponse stats = adminController.getStats();
        assertEquals(before + 2, stats.getParents());
    }

    @Test
    void getStats_returnsCorrectStudentCount() {
        long before = childRepository.count();
        Parent parent = parentRepository.save(new Parent("Carol", 5));
        childRepository.save(new Child("Tom", 3, parent));

        AdminStatsResponse stats = adminController.getStats();
        assertEquals(before + 1, stats.getStudents());
    }

    @Test
    void getStats_returnsCorrectCreditsPurchased() {
        long creditsBefore = parentRepository.sumAllCredits();
        parentRepository.save(new Parent("Dan", 50));
        parentRepository.save(new Parent("Eve", 30));

        AdminStatsResponse stats = adminController.getStats();
        assertEquals(creditsBefore + 80, stats.getCreditsPurchased());
    }
}
