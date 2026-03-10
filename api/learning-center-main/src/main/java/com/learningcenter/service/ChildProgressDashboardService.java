package com.learningcenter.service;

import com.learningcenter.dto.ChildProgressDashboardResponse;
import com.learningcenter.entities.Goal;
import com.learningcenter.entities.Progress;
import com.learningcenter.entities.Session;
import com.learningcenter.repository.GoalRepository;
import com.learningcenter.repository.ProgressRepository;
import com.learningcenter.repository.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class ChildProgressDashboardService {

    private final SessionRepository sessionRepository;
    private final GoalRepository goalRepository;
    private final ProgressRepository progressRepository;

    public ChildProgressDashboardService(
            SessionRepository sessionRepository,
            GoalRepository goalRepository,
            ProgressRepository progressRepository
    ) {
        this.sessionRepository = sessionRepository;
        this.goalRepository = goalRepository;
        this.progressRepository = progressRepository;
    }

    public ChildProgressDashboardResponse getChildProgressDashboard(Long parentId, Long childId, String groupBy) {

        if (parentId == null || childId == null) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "parentId and childId are required"),
                    null
            );
        }

        String grouping = normalizeGroupBy(groupBy);
        LocalDateTime now = LocalDateTime.now();

        List<Session> sessions = sessionRepository.findDashboardSessionsByParentIdAndChildId(parentId, childId);
        List<SessionWithTime> completed = new ArrayList<>();
        List<SessionWithTime> upcoming = new ArrayList<>();

        for (Session session : sessions) {
            if (session.getTimeslot() == null) continue;
            if (session.getTimeslot().getTimeslot() == null) continue;
            if (session.getTimeslot().getTimeslot().getTime() == null) continue;

            LocalDateTime sessionTime = trimToMinute(session.getTimeslot().getTimeslot().getTime());
            if (sessionTime.isBefore(now)) {
                completed.add(new SessionWithTime(session, sessionTime));
            } else {
                upcoming.add(new SessionWithTime(session, sessionTime));
            }
        }

        int totalCompletedSessions = completed.size();

        LocalDateTime mostRecentSessionDate = null;
        for (SessionWithTime swt : completed) {
            if (mostRecentSessionDate == null || swt.sessionTime.isAfter(mostRecentSessionDate)) {
                mostRecentSessionDate = swt.sessionTime;
            }
        }

        Map<String, Integer> sessionsOverTimeMap = new TreeMap<>();
        for (SessionWithTime swt : completed) {
            String bucket = grouping.equals("month")
                    ? YearMonth.from(swt.sessionTime).toString()
                    : formatIsoWeek(swt.sessionTime);

            sessionsOverTimeMap.put(bucket, sessionsOverTimeMap.getOrDefault(bucket, 0) + 1);
        }

        List<ChildProgressDashboardResponse.ChartPoint> sessionsOverTime = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sessionsOverTimeMap.entrySet()) {
            sessionsOverTime.add(new ChildProgressDashboardResponse.ChartPoint(entry.getKey(), entry.getValue(), null, null));
        }

        Map<String, Integer> subjectMap = new TreeMap<>();
        for (SessionWithTime swt : completed) {
            String subjectName = "Unknown";
            if (swt.session.getSubject() != null && swt.session.getSubject().getName() != null) {
                subjectName = swt.session.getSubject().getName();
            }

            subjectMap.put(subjectName, subjectMap.getOrDefault(subjectName, 0) + 1);
        }

        List<ChildProgressDashboardResponse.ChartPoint> subjectBreakdown = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : subjectMap.entrySet()) {
            subjectBreakdown.add(new ChildProgressDashboardResponse.ChartPoint(entry.getKey(), entry.getValue(), null, null));
        }

        Set<String> currentSubjectSet = new LinkedHashSet<>();
        for (SessionWithTime swt : upcoming) {
            if (swt.session.getSubject() != null && swt.session.getSubject().getName() != null) {
                currentSubjectSet.add(swt.session.getSubject().getName());
            }
        }

        List<ChildProgressDashboardResponse.ChartPoint> currentSubjects = new ArrayList<>();
        for (String subject : currentSubjectSet) {
            currentSubjects.add(new ChildProgressDashboardResponse.ChartPoint(subject, 1, null, null));
        }

        completed.sort((a, b) -> b.sessionTime.compareTo(a.sessionTime));

        List<ChildProgressDashboardResponse.ChartPoint> lastTutorNotes = new ArrayList<>();
        int count = 0;

        for (SessionWithTime swt : completed) {
            if (count == 3) break;

            String note = swt.session.getSessionNotes();
            if (note == null || note.isBlank()) {
                continue;
            }

            String tutorName = "Tutor";
            if (swt.session.getTimeslot() != null
                    && swt.session.getTimeslot().getTutor() != null
                    && swt.session.getTimeslot().getTutor().getName() != null) {
                tutorName = swt.session.getTimeslot().getTutor().getName();
            }

            lastTutorNotes.add(new ChildProgressDashboardResponse.ChartPoint(
                    tutorName,
                    0,
                    swt.sessionTime,
                    note.trim()
            ));

            count++;
        }

        List<ChildProgressDashboardResponse.GoalProgress> goals = new ArrayList<>();
        List<Goal> childGoals = goalRepository.findByChild_ChildId(childId);

        for (Goal goal : childGoals) {
            List<Progress> progressList = progressRepository.findByGoal_GoalIdOrderByProgressIdDesc(goal.getGoalId());

            Integer percentageComplete = 0;
            if (!progressList.isEmpty() && progressList.get(0).getPercentageComplete() != null) {
                percentageComplete = progressList.get(0).getPercentageComplete();
            }

            String subjectName = "Unknown";
            if (goal.getSubject() != null && goal.getSubject().getName() != null) {
                subjectName = goal.getSubject().getName();
            }

            goals.add(new ChildProgressDashboardResponse.GoalProgress(
                    goal.getGoalId(),
                    subjectName,
                    goal.getTitle(),
                    percentageComplete
            ));
        }

        return new ChildProgressDashboardResponse(
                childId,
                totalCompletedSessions,
                mostRecentSessionDate,
                sessionsOverTime,
                subjectBreakdown,
                currentSubjects,
                lastTutorNotes,
                goals
        );
    }

    private String normalizeGroupBy(String groupBy) {
        String grouping = "week";
        if (groupBy != null && !groupBy.isBlank()) {
            grouping = groupBy.trim().toLowerCase();
        }

        if (!grouping.equals("week") && !grouping.equals("month")) {
            throw new ErrorResponseException(
                    HttpStatus.BAD_REQUEST,
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "groupBy must be 'week' or 'month'"),
                    null
            );
        }
        return grouping;
    }

    private static String formatIsoWeek(LocalDateTime dt) {
        WeekFields wf = WeekFields.ISO;
        int week = dt.get(wf.weekOfWeekBasedYear());
        int year = dt.get(wf.weekBasedYear());
        return String.format("%d-W%02d", year, week);
    }

    private static class SessionWithTime {
        private final Session session;
        private final LocalDateTime sessionTime;

        private SessionWithTime(Session session, LocalDateTime sessionTime) {
            this.session = session;
            this.sessionTime = sessionTime;
        }
    }

    private static LocalDateTime trimToMinute(LocalDateTime dt) {
        if (dt == null) return null;
        return dt.withSecond(0).withNano(0);
    }
}