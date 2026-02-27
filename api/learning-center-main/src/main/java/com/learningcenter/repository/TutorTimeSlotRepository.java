package com.learningcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.learningcenter.entities.TutorTimeslot;

public interface TutorTimeSlotRepository extends CrudRepository<TutorTimeslot, Long> {
    
    @Query("SELECT t FROM TutorTimeslot t WHERE t.tutor.tutorId = :tutorId")
    List<TutorTimeslot> findByTutorId(Long tutorId);

    @Query("SELECT t FROM TutorTimeslot t WHERE t.tutor.tutorId = :tutorId AND NOT EXISTS (SELECT s FROM Session s WHERE s.tutorTimeslot.tutorTimeslotId = t.tutorTimeslotId)")
    List<TutorTimeslot> findAvailableByTutorId(Long tutorId);

}