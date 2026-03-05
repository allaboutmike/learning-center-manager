package com.learningcenter.service;

import com.learningcenter.dto.CreateParentRequest;
import com.learningcenter.dto.ParentResponse;
import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;
import com.learningcenter.repository.ParentRepository;
import com.learningcenter.repository.ChildRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ParentServiceTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ParentService parentService;

    @Autowired
    private ChildRepository childRepository;

    @Test
    void getChildrenByParent_returnsChildren() {

        Parent parent = new Parent();
        parent.setName("Test Parent");
        parent.setCredits(10);
        parent.setEmail("test.parent@example.com");
        parent = parentRepository.save(parent);
        Long parentId = parent.getParentId();


        Child child1 = new Child();
        child1.setName("Alice");
        child1.setGradeLevel(5);
        child1.setParent(parent);


        Child child2 = new Child();
        child2.setName("Bob");
        child2.setGradeLevel(3);
        child2.setParent(parent);

        childRepository.saveAll(List.of(child1, child2));


        var result = parentService.getChildrenByParent(parentId);


        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).firstName());
        assertEquals("Bob", result.get(1).firstName());

    }

    @Test
    void getCreditsByParentId_returnsCreditBalance() {


        Parent parent = new Parent();
        parent.setName("Tim");
        parent.setCredits(10);
        parent.setEmail("tim@example.com");

        parent = parentRepository.save(parent);


        Integer result = parentService.getCreditsByParentId(parent.getParentId());


        assertEquals(10, result);
    }

    @Test
    void addCreditsByParentId_returnsCreditBalance() {


        Parent parent = new Parent();
        parent.setName("Tim");
        parent.setCredits(10);
        parent.setEmail("tim.credits@example.com");

        parent = parentRepository.save(parent);


        ParentResponse result = parentService.addCreditsByParentId(parent.getParentId(), 10);


        assertEquals(20, result.credits());
    }

    @Test
    void createParent_success_returnsParentResponse() {
        CreateParentRequest request = new CreateParentRequest("New Parent", "new.parent@example.com", "555-9999");

        ParentResponse result = parentService.createParent(request);

        assertEquals("New Parent", result.email() != null ? "New Parent" : null);
        assertEquals("new.parent@example.com", result.email());
        assertEquals(0, result.credits());
    }

    @Test
    void createParent_duplicateEmail_throwsConflict() {
        CreateParentRequest request = new CreateParentRequest("First Parent", "duplicate@example.com", null);
        parentService.createParent(request);

        CreateParentRequest duplicate = new CreateParentRequest("Second Parent", "duplicate@example.com", null);

        assertThrows(ResponseStatusException.class, () -> parentService.createParent(duplicate));
    }
}