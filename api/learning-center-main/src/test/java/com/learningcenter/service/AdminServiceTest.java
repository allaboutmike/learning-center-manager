package com.learningcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.learningcenter.dto.AdminStatsResponse;
import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;
import com.learningcenter.repository.ChildRepository;
import com.learningcenter.repository.ParentRepository;
import com.learningcenter.repository.TutorRepository;

@SpringBootTest
@Transactional
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Test
    void getStats_returnsNonNullResponse() {
        AdminStatsResponse stats = adminService.getStats();
        assertNotNull(stats);
    }

    @Test
    void getStats_parentCount_reflectsPersistedParents() {
        long before = parentRepository.count();
        Parent laura = new Parent("Laura", "laura@example.com", null);
        laura.setCredits(15);
        parentRepository.save(laura);
        Parent james = new Parent("James", "james@example.com", null);
        james.setCredits(25);
        parentRepository.save(james);

        AdminStatsResponse stats = adminService.getStats();
        assertEquals(before + 2, stats.getParents());
    }

    @Test
    void getStats_studentCount_reflectsPersistedChildren() {
        long before = childRepository.count();
        Parent sara = new Parent("Sara", "sara@example.com", null);
        sara.setCredits(10);
        Parent parent = parentRepository.save(sara);
        childRepository.save(new Child("Lily", 4, parent));
        childRepository.save(new Child("Max", 6, parent));

        AdminStatsResponse stats = adminService.getStats();
        assertEquals(before + 2, stats.getStudents());
    }

    @Test
    void getStats_tutorCount_reflectsPersistedTutors() {
        AdminStatsResponse stats = adminService.getStats();
        assertEquals(tutorRepository.count(), stats.getTutors());
    }

    @Test
    void getStats_creditsPurchased_reflectsSumOfAllParentCredits() {
        long creditsBefore = parentRepository.sumAllCredits();
        Parent nick = new Parent("Nick", "nick@example.com", null);
        nick.setCredits(100);
        parentRepository.save(nick);
        Parent mia = new Parent("Mia", "mia@example.com", null);
        mia.setCredits(50);
        parentRepository.save(mia);

        AdminStatsResponse stats = adminService.getStats();
        assertEquals(creditsBefore + 150, stats.getCreditsPurchased());
    }

    @Test
    void getStats_withNoAdditionalData_returnsConsistentCounts() {
        AdminStatsResponse stats = adminService.getStats();

        assertEquals(parentRepository.count(), stats.getParents());
        assertEquals(childRepository.count(), stats.getStudents());
        assertEquals(tutorRepository.count(), stats.getTutors());
        assertEquals(parentRepository.sumAllCredits(), stats.getCreditsPurchased());
    }
}
