package com.learningcenter.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;
import com.learningcenter.entities.Subject;
import com.learningcenter.entities.Timeslot;
import com.learningcenter.entities.Tutor;
import com.learningcenter.entities.TutorTimeslot;

@DataJpaTest
public class TutorRepositoryIntegrationTest {

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    public void givenNewTutorWhenSaveThenSuccess() {
        Tutor newTutor = new Tutor("John", 1, 12, "http://example.com/john", "Experienced tutor in math and science.");
        Tutor newTutorAdded = tutorRepository.save(newTutor);
        assertThat(entityManager.find(Tutor.class, newTutorAdded.getTutorId())).isEqualTo(newTutorAdded);
    }

    //Test to delete tutor
    @Test
    public void givenExistingTutorWhenDeleteThenSuccess() {
        Tutor newTutor = new Tutor("John", 1, 12, "http://example.com/john", "Experienced tutor in math and science.");
        Tutor newTutorAdded = tutorRepository.save(newTutor);
        tutorRepository.delete(newTutorAdded);
        assertThat(entityManager.find(Tutor.class, newTutorAdded.getTutorId())).isNull();
    }

    //Test to update tutor
    @Test
    public void givenExistingTutorWhenUpdateThenSuccess() {
        Tutor newTutor = new Tutor("John", 1, 12, "http://example.com/john", "Experienced tutor in math and science.");
        Tutor newTutorAdded = tutorRepository.save(newTutor);
        newTutorAdded.setName("John Doe");
        tutorRepository.save(newTutorAdded);
        assertThat(entityManager.find(Tutor.class, newTutorAdded.getTutorId()).getName()).isEqualTo("John Doe");
    }

    //Test to find tutors by subject
    @Test
    public void givenSubject_whenFindTutorsBySubject_thenSuccess() {
        Subject math = new Subject("Math test");
        Subject science = new Subject("Science test");
        entityManager.persist(math);
        entityManager.persist(science);

        Tutor tutor1 = new Tutor("John", 1, 12, "http://example.com/john", "Experienced tutor in math and science.");
        tutor1.getSubjects().add(math);
        entityManager.persist(tutor1);

        Tutor tutor2 = new Tutor("Jane", 2, 10, "http://example.com/jane", "Experienced tutor in math and science.");
        tutor2.getSubjects().add(science);
        entityManager.persist(tutor2);

        List<Tutor> tutors = tutorRepository.findTutorsBySubject("Math test");
        assertThat(tutors.size()).isEqualTo(1);
    }

    //test to find tutors by a child's grade level
    @Test
    public void givenChildId_whenFindTutorsByGradeLevel_thenSuccess() {
        Tutor tutor1 = new Tutor("John", 99, 99, "http://example.com/john", "Experienced tutor in math and science.");
        entityManager.persist(tutor1);
        Tutor tutor2 = new Tutor("Jane", 1, 1, "http://example.com/jane", "Experienced tutor in math and science.");
        entityManager.persist(tutor2);

        //Create a parent and child to test the query
        Parent parent = new Parent("Bob Smith");
        Child child = new Child("Alice Smith", 99, parent);
        Child child2 = new Child("Charlie Smith", 100, parent);
        parent.getChild().add(child);
        parent.getChild().add(child2);
        entityManager.persist(parent);
        entityManager.persist(child);
        entityManager.persist(child2);

        List<Tutor> tutorsForChild1 = tutorRepository.findTutorsByChildGradeLevel(child.getChildId());
        List<Tutor> tutorsForChild2 = tutorRepository.findTutorsByChildGradeLevel(child2.getChildId());
        assertThat(tutorsForChild1.size()).isEqualTo(1);
        assertThat(tutorsForChild2.size()).isEqualTo(0);
    }

    //test to find a tutor by availability
    @Test
    public void givenTimeSlotWhenFindTutorsByAvailabilityThenSuccess() {
        Tutor tutor1 = new Tutor("John", 1, 12, "http://example.com/john", "Experienced tutor in math and science.");
        Timeslot timeslot1 = new Timeslot(LocalDateTime.of(2024, 6, 1, 10, 0, 0));
        Tutor tutor2 = new Tutor("Jane", 1, 12, "http://example.com/jane", "Experienced tutor in math and science.");
        Timeslot timeslot2 = new Timeslot(LocalDateTime.of(2024, 6, 1, 11, 0, 0));

        entityManager.persist(timeslot1);
        entityManager.persist(timeslot2);
        entityManager.persist(tutor1);
        entityManager.persist(tutor2);

        //Giving this tutor one time slot
        TutorTimeslot tutorTimeslot1 = new TutorTimeslot(tutor1, timeslot1);
        tutor1.getTutorTimeSlots().add(tutorTimeslot1);
        entityManager.persist(tutorTimeslot1);

        //this one tutor has two time slots so we can test that query works when a tutor has multiple time slots
        TutorTimeslot tutorTimeslot2 = new TutorTimeslot(tutor2, timeslot1);
        TutorTimeslot tutorTimeslot3 = new TutorTimeslot(tutor2, timeslot2);
        tutor2.getTutorTimeSlots().add(tutorTimeslot2);
        tutor2.getTutorTimeSlots().add(tutorTimeslot3);
        entityManager.persist(tutorTimeslot2);
        entityManager.persist(tutorTimeslot3);

        List<Tutor> noTutors = tutorRepository.findTutorsByAvailability(Timestamp.valueOf("2024-06-01 9:00:00").toLocalDateTime());
        List<Tutor> tutors = tutorRepository.findTutorsByAvailability(Timestamp.valueOf("2024-06-01 10:00:00").toLocalDateTime());
        List<Tutor> oneTutor = tutorRepository.findTutorsByAvailability(Timestamp.valueOf("2024-06-01 11:00:00").toLocalDateTime());


        assertThat(noTutors.size()).isEqualTo(0);
        assertThat(tutors.size()).isEqualTo(2);
        assertThat(oneTutor.size()).isEqualTo(1);
    }

    //test to find tutors by a child's grade level and subject
    @Test
    public void givenChildIdAndSubject_whenFindTutorsByGradeLevelAndSubject_thenSuccess() {
        Subject math = new Subject("Math test");
        Subject science = new Subject("Science test");
        entityManager.persist(math);
        entityManager.persist(science);

        Tutor tutor1 = new Tutor("John", 8, 12, "http://example.com/john", "Experienced tutor in math and science.");
        tutor1.getSubjects().add(math);
        entityManager.persist(tutor1);
        Tutor tutor2 = new Tutor("Jane", 1, 9, "http://example.com/jane", "Experienced tutor in math and science.");
        tutor2.getSubjects().add(science);
        entityManager.persist(tutor2);
        Tutor tutor3 = new Tutor("Jack", 1, 12, "http://example.com/jack", "Experienced tutor in math and science.");
        tutor3.getSubjects().add(math);
        tutor3.getSubjects().add(science);
        entityManager.persist(tutor3);

        //Create a parent and child to test the query
        Parent parent = new Parent("Bob Smith");
        Child child = new Child("Alice Smith", 8, parent);
        Child child2 = new Child("Charlie Smith", 4, parent);
        parent.getChild().add(child);
        parent.getChild().add(child2);
        entityManager.persist(parent);
        entityManager.persist(child);
        entityManager.persist(child2);

        List<Tutor> tutorsForChild1 = tutorRepository.findTutorsByChildGradeLevelAndSubject(child.getChildId(), "Math test");
        List<Tutor> tutorsForChild2 = tutorRepository.findTutorsByChildGradeLevelAndSubject(child2.getChildId(), "Science test");
        assertThat(tutorsForChild1.size()).isEqualTo(2);
        assertThat(tutorsForChild2.size()).isEqualTo(2);
    }

    //test to find tutors by a child's grade level and subject and availability
    @Test
    public void givenChildIdAndSubjectAndTimeSlotWhenFindTutorsByGradeLevelSubjectAndAvailabilityThenSuccess() {
        //Creating Subjects
        Subject math = new Subject("Math");
        Subject science = new Subject("Science");
        Subject english = new Subject("English");
        entityManager.persist(math);
        entityManager.persist(science);
        entityManager.persist(english);

        //Creating Tutors and giving them subjects
        Tutor tutor1 = new Tutor("John", 9, 12, "http://example.com/john", "Experienced tutor in math and science.");
        tutor1.getSubjects().add(math);
        entityManager.persist(tutor1);
        Tutor tutor2 = new Tutor("Jane", 5, 8, "http://example.com/jane", "Experienced tutor in math and science.");
        tutor2.getSubjects().add(science);
        entityManager.persist(tutor2);
        Tutor tutor3 = new Tutor("Jack", 1, 12, "http://example.com/jack", "Experienced tutor in math and science.");
        tutor3.getSubjects().add(math);
        tutor3.getSubjects().add(science);
        tutor3.getSubjects().add(english);
        entityManager.persist(tutor3);

        //Creating time slots
        Timeslot timeslot1 = new Timeslot(LocalDateTime.of(2024, 6, 1, 10, 0, 0));
        Timeslot timeslot2 = new Timeslot(LocalDateTime.of(2024, 6, 1, 11, 0, 0));
        entityManager.persist(timeslot1);
        entityManager.persist(timeslot2);

        //Linking a time slot to tutor 1 and tutor 2 and both time slots to tutor 3
        TutorTimeslot tutorTimeslot1 = new TutorTimeslot(tutor1, timeslot1);
        tutor1.getTutorTimeSlots().add(tutorTimeslot1);
        entityManager.persist(tutorTimeslot1);

        TutorTimeslot tutorTimeslot2 = new TutorTimeslot(tutor2, timeslot2);
        tutor2.getTutorTimeSlots().add(tutorTimeslot2);
        entityManager.persist(tutorTimeslot2);

        TutorTimeslot tutorTimeslot3 = new TutorTimeslot(tutor3, timeslot1);
        TutorTimeslot tutorTimeslot4 = new TutorTimeslot(tutor3, timeslot2);
        tutor3.getTutorTimeSlots().add(tutorTimeslot3);
        tutor3.getTutorTimeSlots().add(tutorTimeslot4);
        entityManager.persist(tutorTimeslot3);
        entityManager.persist(tutorTimeslot4);

        //Create a parent and child to test the query
        Parent parent = new Parent("Bob Smith");
        Child child = new Child("Alice Smith", 10, parent);
        Child child2 = new Child("Charlie Smith", 4, parent);
        parent.getChild().add(child);
        parent.getChild().add(child2);
        entityManager.persist(parent);
        entityManager.persist(child);
        entityManager.persist(child2);

        List<Tutor> tutorsForChild1 = tutorRepository.findTutorsByGradeLevelSubjectAndAvailability(child.getChildId(), "Math", Timestamp.valueOf("2024-06-01 10:00:00").toLocalDateTime());
        List<Tutor> tutorsForChild2 = tutorRepository.findTutorsByGradeLevelSubjectAndAvailability(child2.getChildId(), "Science", Timestamp.valueOf("2024-06-01 11:00:00").toLocalDateTime());
        List<Tutor> tutorsNotAvailable = tutorRepository.findTutorsByGradeLevelSubjectAndAvailability(child2.getChildId(), "Science", Timestamp.valueOf("2024-06-01 12:00:00").toLocalDateTime());
        List<Tutor> tutorsWrongSubject = tutorRepository.findTutorsByGradeLevelSubjectAndAvailability(child.getChildId(), "History", Timestamp.valueOf("2024-06-01 10:00:00").toLocalDateTime());

        assertThat(tutorsWrongSubject.size()).isEqualTo(0);
        assertThat(tutorsNotAvailable.size()).isEqualTo(0);
        assertThat(tutorsForChild1.size()).isEqualTo(2);
        assertThat(tutorsForChild2.size()).isEqualTo(1);
    }

    //Test to find tutors by grade level regardless of child grade level
    @Test
    public void givenGradeLevel_whenFindTutorsByGradeLevel_thenSuccess() {
        Tutor tutor1 = new Tutor("John", 99, 100, "http://example.com/john", "Experienced tutor in math and science.");
        Tutor tutor2 = new Tutor("Jane", 98, 98, "http://example.com/jane", "Experienced tutor in math and science.");
        
        entityManager.persist(tutor1);
        entityManager.persist(tutor2);

        List<Tutor> tutors = tutorRepository.findTutorsByGradeLevel(99);
        assertThat(tutors.size()).isEqualTo(1);
    }
}
