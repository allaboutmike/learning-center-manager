package com.learningcenter.repository;

import com.learningcenter.entities.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByGoal_GoalIdOrderByProgressIdDesc(Long goalId);
}
