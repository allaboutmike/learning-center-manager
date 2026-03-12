package com.learningcenter.service;

import com.learningcenter.dto.CreateProgressRequest;
import com.learningcenter.entities.Goal;
import com.learningcenter.entities.Progress;
import com.learningcenter.entities.Session;
import com.learningcenter.repository.GoalRepository;
import com.learningcenter.repository.ProgressRepository;
import com.learningcenter.repository.SessionRepository;
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
}