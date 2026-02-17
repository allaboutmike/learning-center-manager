package com.learningcenter.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.learningcenter.entities.Tutor;



public interface TutorRepository extends CrudRepository<Tutor, Long> {

    // query method to find tutors by subject
    @Query("SELECT t from Tutor t JOIN t.subjects s WHERE s.name = :subjectName")
    List<Tutor> findTutorsBySubject(String subjectName);

    //Query to find tutors that can teach a grade level
    @Query("SELECT t FROM Tutor t WHERE :gradeLevel BETWEEN t.minGradeLevel AND t.maxGradeLevel")
    List<Tutor> findTutorsByGradeLevel(int gradeLevel);

    //Query to find tutors that are available at a specific time
    @Query("SELECT t from Tutor t JOIN t.tutorTimeSlots tts JOIN tts.timeslot timeslot WHERE timeslot.time = :timeSlot")
    List<Tutor> findTutorsByAvailability(LocalDateTime timeSlot);

    //Query to find tutors that can teach a grade level and subject
    @Query("SELECT t from Tutor t JOIN t.subjects s, Child c WHERE c.childId = :childId and s.name = :subjectName and c.grade_level BETWEEN t.minGradeLevel and t.maxGradeLevel")
    List<Tutor> findTutorsByGradeLevelAndSubject(Long childId, String subjectName);

    //Query to find tutors that can teach a grade level and subject and are available at a specific time
    @Query("SELECT t from Tutor t JOIN t.subjects s JOIN t.tutorTimeSlots tts JOIN tts.timeslot timeslot, Child c WHERE c.childId = :childId and s.name = :subjectName and c.grade_level BETWEEN t.minGradeLevel and t.maxGradeLevel and timeslot.time = :timeSlot")
    List<Tutor> findTutorsByGradeLevelSubjectAndAvailability(Long childId, String subjectName, LocalDateTime timeSlot);

}
