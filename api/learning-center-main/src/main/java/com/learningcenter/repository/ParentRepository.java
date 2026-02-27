package com.learningcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;

public interface ParentRepository extends CrudRepository<Parent, Long> {

    //Query method to find all children by parent
    @Query("SELECT c FROM Parent p JOIN p.child c WHERE p.parentId = :parentId")
    List<Child> listOfChildrenByParentId(Long parentId);

}
