package com.learningcenter.repository;

import com.learningcenter.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByChild_ChildId(Long childId);

}
