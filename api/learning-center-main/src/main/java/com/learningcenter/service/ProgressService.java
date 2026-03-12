package com.learningcenter.service;

import com.learningcenter.dto.CreateProgressRequest;
import com.learningcenter.dto.ProgressResponse;
import com.learningcenter.entities.Goal;
import com.learningcenter.entities.Progress;
import com.learningcenter.entities.Session;
import com.learningcenter.repository.GoalRepository;
import com.learningcenter.repository.ProgressRepository;
import com.learningcenter.repository.SessionRepository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final GoalRepository goalRepository;
    private final SessionRepository sessionRepository;

    public ProgressService(
            ProgressRepository progressRepository,
            GoalRepository goalRepository,
            SessionRepository sessionRepository
    ) {
        this.progressRepository = progressRepository;
        this.goalRepository = goalRepository;
        this.sessionRepository = sessionRepository;
    }

    public Progress createProgress(Long childId, Long goalId, CreateProgressRequest request) {

        Goal goal = goalRepository.findById(goalId).orElseThrow();
        Session session = sessionRepository.findById(request.sessionId()).orElseThrow();

        Progress progress = new Progress(
                goal,
                request.percentageComplete(),
                session
        );

        return progressRepository.save(progress);
    }

    public ProgressResponse createProgressForSession(Long sessionId, CreateProgressRequest request) {
        Session session = sessionRepository.findById(sessionId).orElseThrow();

        Long childId = session.getChild().getChildId();
        Long subjectId = session.getSubject().getSubjectId();

        List<Goal> goals = goalRepository.findByChild_ChildIdAndSubject_SubjectId(childId, subjectId).orElseThrow();
        
        if (goals.isEmpty()) {
            throw new NoSuchElementException("No goals found for child and subject");
        }

        Progress progress = new Progress(
                goals.get(0),
                request.percentageComplete(),
                session
        );

        Progress savedProgress = progressRepository.save(progress);

        return new ProgressResponse(savedProgress);
    }
}