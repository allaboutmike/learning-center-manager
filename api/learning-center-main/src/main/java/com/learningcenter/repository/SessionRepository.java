package com.learningcenter.repository;

import com.learningcenter.entities.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {

    @Query("SELECT s FROM Session s WHERE s.child.childId = :childId")
    List<Session> findSessionsByChildId(Long childId);

    @Query("SELECT s FROM Session s WHERE s.child.childId = :childId AND s.child.parent.parentId = :parentId")
    List<Session> findSessionsByParentIdAndChildId(@Param("parentId") Long parentId, @Param("childId") Long childId);
}
