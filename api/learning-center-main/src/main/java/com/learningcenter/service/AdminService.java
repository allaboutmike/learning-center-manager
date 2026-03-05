package com.learningcenter.service;

import org.springframework.stereotype.Service;

import com.learningcenter.dto.AdminStatsResponse;
import com.learningcenter.repository.ChildRepository;
import com.learningcenter.repository.ParentRepository;
import com.learningcenter.repository.TutorRepository;

@Service
public class AdminService {

    private ParentRepository parentRepository;
    private ChildRepository childRepository;
    private TutorRepository tutorRepository;

    public AdminService(ParentRepository parentRepository, ChildRepository childRepository, TutorRepository tutorRepository) {
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
        this.tutorRepository = tutorRepository;
    }

    public AdminStatsResponse getStats() {
        long parents = parentRepository.count();
        long students = childRepository.count();
        long tutors = tutorRepository.count();
        long creditsPurchased = parentRepository.sumAllCredits();
        return new AdminStatsResponse(parents, students, tutors, creditsPurchased);
    }
}
