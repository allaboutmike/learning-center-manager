package com.learningcenter.repository;

import com.learningcenter.entities.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
}
