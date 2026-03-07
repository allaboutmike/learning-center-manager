package com.learningcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.learningcenter.entities.Session;

public interface SessionRepository extends CrudRepository<Session, Long> {

    @Query("SELECT s FROM Session s WHERE s.child.childId = :childId")
    List<Session> findSessionsByChildId(Long childId);

    @Query("SELECT s FROM Session s WHERE s.child.childId = :childId AND s.child.parent.parentId = :parentId")
    List<Session> findSessionsByParentIdAndChildId(@Param("parentId") Long parentId, @Param("childId") Long childId);

    @Query("SELECT s FROM Session s JOIN FETCH s.subject subj JOIN FETCH s.tutorTimeslot tts JOIN FETCH tts.timeslot ts JOIN FETCH tts.tutor t WHERE s.child.childId = :childId AND s.child.parent.parentId = :parentId")
    List<Session> findDashboardSessionsByParentIdAndChildId(@Param("parentId") Long parentId, @Param("childId") Long childId);

    @Query("SELECT s FROM Session s JOIN FETCH s.tutorTimeslot tts JOIN FETCH tts.tutor t JOIN FETCH tts.timeslot ts JOIN FETCH s.subject subj JOIN FETCH s.child c WHERE tts.tutor.tutorId = :tutorId")
    List<Session> findSessionsByTutorId(@Param("tutorId") Long tutorId);
}
