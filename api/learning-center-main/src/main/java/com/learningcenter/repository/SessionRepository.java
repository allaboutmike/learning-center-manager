package com.learningcenter.repository;

import com.learningcenter.entities.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {

    @Query("SELECT s FROM Session s WHERE s.child.child_id = :childId")
    List<Session> findSessionsByChildId(Long childId);
}
