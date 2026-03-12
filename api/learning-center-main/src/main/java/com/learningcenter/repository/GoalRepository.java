package com.learningcenter.repository;

import com.learningcenter.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByChild_ChildId(Long childId);
    Optional<List<Goal>> findByChild_ChildIdAndSubject_SubjectId(Long childId, Long subjectId);
}
