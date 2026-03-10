package com.learningcenter.service;

import com.learningcenter.dto.CreateGoalRequest;
import com.learningcenter.entities.Child;
import com.learningcenter.entities.Goal;
import com.learningcenter.entities.Subject;
import com.learningcenter.repository.ChildRepository;
import com.learningcenter.repository.GoalRepository;
import com.learningcenter.repository.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final ChildRepository childRepository;
    private final SubjectRepository subjectRepository;

    public GoalService(
            GoalRepository goalRepository,
            ChildRepository childRepository,
            SubjectRepository subjectRepository
    ) {
        this.goalRepository = goalRepository;
        this.childRepository = childRepository;
        this.subjectRepository = subjectRepository;
    }

    public Goal createGoal(Long childId, CreateGoalRequest request) {
        if (childId == null) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "childId is required"),
                    null
            );
        }

        if (request == null) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "request body is required"),
                    null
            );
        }

        if (request.subjectId() == null) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "subjectId is required"),
                    null
            );
        }

        if (request.title() == null || request.title().isBlank()) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "title is required"),
                    null
            );
        }

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ErrorResponseException(
                        HttpStatus.BAD_REQUEST,
                        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid childId"),
                        null
                ));

        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new ErrorResponseException(
                        HttpStatus.BAD_REQUEST,
                        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid subjectId"),
                        null
                ));

        Goal goal = new Goal(child, subject, request.title().trim());

        return goalRepository.save(goal);
    }
}