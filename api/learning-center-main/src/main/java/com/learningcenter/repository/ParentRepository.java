package com.learningcenter.repository;

import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;
import com.learningcenter.entities.Tutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParentRepository extends CrudRepository<Parent, Long> {


//Query method to find all children by parent

    @Query("SELECT c FROM Parent p JOIN p.child c WHERE p.parentId = :parentId")
List<Child> listOfChildrenByParentId(Long parentId);









}
