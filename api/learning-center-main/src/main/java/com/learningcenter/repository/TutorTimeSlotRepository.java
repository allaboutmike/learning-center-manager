package com.learningcenter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.learningcenter.entities.TutorTimeslot;

public interface TutorTimeSlotRepository extends CrudRepository<TutorTimeslot, Long> {
    
    @Query("SELECT t FROM TutorTimeslot t WHERE t.tutor.tutorId = :tutorId")
    Iterable<TutorTimeslot> findByTutorId(Long tutorId);
}
