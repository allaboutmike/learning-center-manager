package com.learningcenter.service;

import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent; // Ensure you have the Parent entity
import com.learningcenter.repository.ParentRepository;
import com.learningcenter.repository.ChildRepository; // You'll likely need this to set up data
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}