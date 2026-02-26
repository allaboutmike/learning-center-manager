package com.learningcenter.repository;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;

@DataJpaTest
public class ParentRepositoryIntegrationTest {

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    public void givenNewParent_whenSave_thenSuccess() {
        Parent newParent = new Parent("Marley", 25);
        Parent newParentAdded = parentRepository.save(newParent);
        assertThat(entityManager.find(Parent.class, newParentAdded.getParentId())).isEqualTo(newParentAdded);
    }

    //Test to delete parent
    @Test
    public void givenExistingParent_whenDelete_thenSuccess() {
        Parent newParent = new Parent("John", 10);
        Parent newParentAdded = parentRepository.save(newParent);
        parentRepository.delete(newParentAdded);
        assertThat(entityManager.find(Parent.class, newParentAdded.getParentId())).isNull();
    }

    //Test to update parent

    @Test
    public void givenExistingParent_whenUpdate_thenSuccess() {
        Parent newParent = new Parent("John", 10);
        Parent newParentAdded = parentRepository.save(newParent);
        newParentAdded.setName("James");
        parentRepository.save(newParentAdded);
        assertThat(entityManager.find(Parent.class, newParentAdded.getParentId()).getName()).isEqualTo("James");
    }

    //Test to get list of children

    @Test
    public void givenExistingParent_getListOfChildrenByParentId_thenSuccess() {

        Parent parent = new Parent("Bob Smith", 67);
        Child child = new Child("Alice Smith", 5, parent);
        Child child2 = new Child("Charlie Smith", 7, parent);
        parent.getChild().add(child);
        parent.getChild().add(child2);
        entityManager.persist(parent);
        entityManager.persist(child);
        entityManager.persist(child2);

        List<Child> childrenOfParent = parentRepository.listOfChildrenByParentId(parent.getParentId());
        assertThat(childrenOfParent.size()).isEqualTo(2);
    }
}