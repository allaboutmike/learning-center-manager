package com.learningcenter.repository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.learningcenter.entities.Subject;
import com.learningcenter.entities.Tutor;

@DataJpaTest
public class TutorRepositoryIntegrationTest {

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    public void givenNewTutor_whenSave_thenSuccess() {
        Tutor newTutor = new Tutor("John", 1, 12, "http://example.com/john", "Experienced tutor in math and science.");
        Tutor newTutorAdded = tutorRepository.save(newTutor);
        assertThat(entityManager.find(Tutor.class, newTutorAdded.getTutorId())).isEqualTo(newTutorAdded);
    }

    //Test to delete tutor
    @Test
    public void givenExistingTutor_whenDelete_thenSuccess() {
        Tutor newTutor = new Tutor("John", 1, 12, "http://example.com/john", "Experienced tutor in math and science.");
        Tutor newTutorAdded = tutorRepository.save(newTutor);
        tutorRepository.delete(newTutorAdded);
        assertThat(entityManager.find(Tutor.class, newTutorAdded.getTutorId())).isNull();
    }

    //Test to update tutor
    @Test
    public void givenExistingTutor_whenUpdate_thenSuccess() {
        Tutor newTutor = new Tutor("John", 1, 12, "http://example.com/john", "Experienced tutor in math and science.");
        Tutor newTutorAdded = tutorRepository.save(newTutor);
        newTutorAdded.setName("John Doe");
        tutorRepository.save(newTutorAdded);
        assertThat(entityManager.find(Tutor.class, newTutorAdded.getTutorId()).getName()).isEqualTo("John Doe");
    }

    //Test to find tutors by subject
    @Test
    public void givenSubject_whenFindTutorsBySubject_thenSuccess() {
        Subject math = new Subject("Math");
        entityManager.persist(math);

        Tutor tutor1 = new Tutor("John", 1, 12, "http://example.com/john", "Experienced tutor in math and science.");
        tutor1.getSubjects().add(math);
        entityManager.persist(tutor1);

        Tutor tutor2 = new Tutor("Jane", 2, 10, "http://example.com/jane", "Experienced tutor in math and science.");
        tutor2.getSubjects().add(math);
        entityManager.persist(tutor2);

        List<Tutor> tutors = tutorRepository.findTutorsBySubject("Math");
        assertThat(tutors.size()).isEqualTo(2);
    }
}
