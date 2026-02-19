package com.learningcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.learningcenter.entities.TutorTimeslot;

public interface TutorTimeSlotRepository extends CrudRepository<TutorTimeslot, Long> {
    
    @Query("SELECT t FROM TutorTimeslot t WHERE t.tutor.tutorId = :tutorId")
    List<TutorTimeslot> findByTutorId(Long tutorId);
}