package com.learningcenter.repository;

import com.learningcenter.entities.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {
    List<Session> findByStudentId(Long student_id);
}
