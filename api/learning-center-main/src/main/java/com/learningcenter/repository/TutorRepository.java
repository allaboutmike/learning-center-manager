package com.learningcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.learningcenter.entities.Tutor;



public interface TutorRepository extends CrudRepository<Tutor, Long> {

    // query method to find tutors by subject
    @Query("SELECT t from Tutor t JOIN t.subjects s WHERE s.name = :subjectName")
    List<Tutor> findTutorsBySubject(String subjectName);
}
