package com.learningcenter.repository;

import com.learningcenter.entities.Child;
import org.springframework.data.repository.CrudRepository;

public interface ChildRepository extends CrudRepository<Child, Long> {
}
