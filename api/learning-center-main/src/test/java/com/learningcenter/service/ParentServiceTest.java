package com.learningcenter.service;

import com.learningcenter.dto.ChildResponse;
import com.learningcenter.entities.Child;
import com.learningcenter.repository.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParentServiceTest {
    @Mock
    private ParentRepository parentRepository;
    private ParentService parentService;

    @BeforeEach
    void setUp() {
        parentService = new ParentService(parentRepository);
    }

    @Test
    void getChildrenByParent_returnsChildren() {

        Long parentId = 1L;

        Child child1 = new Child();
        child1.setChildId(3L);
        child1.setName("Alice");
        child1.setGradeLevel(5);

        Child child2 = new Child();
        child2.setChildId(4L);
        child2.setName("Bob");
        child2.setGradeLevel(3);

        when(parentRepository.listOfChildrenByParentId(parentId))
                .thenReturn(List.of(child1, child2));

        var result = parentService.getChildrenByParent(parentId);

        assertEquals(2, result.size());

        assertEquals("Alice", result.get(0).firstName());
        assertEquals("Bob", result.get(1).firstName());

        verify(parentRepository).listOfChildrenByParentId(parentId);
    }
}