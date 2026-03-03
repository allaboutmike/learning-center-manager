package com.learningcenter.service;

import com.learningcenter.dto.ChildProgressDashboardResponse;
import com.learningcenter.entities.Session;
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

    public ChildProgressDashboardService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // -----------------------------
    // REAL dashboard (from DB)
    // -----------------------------
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

        List<Session> sessions = sessionRepository.findSessionsByParentIdAndChildId(parentId, childId);

        List<SessionWithTime> completed = new ArrayList<>();
        List<SessionWithTime> upcoming = new ArrayList<>();

        for (Session session : sessions) {

            if (session.getTimeslot() == null) continue;
            if (session.getTimeslot().getTimeslot() == null) continue;
            if (session.getTimeslot().getTimeslot().getTime() == null) continue;

            LocalDateTime sessionTime = session.getTimeslot().getTimeslot().getTime();

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

        // Chart 1: sessions over time
        Map<String, Integer> sessionsOverTimeMap = new TreeMap<>();
        for (SessionWithTime swt : completed) {
            String bucket = grouping.equals("month")
                    ? YearMonth.from(swt.sessionTime).toString()
                    : formatIsoWeek(swt.sessionTime);

            if (!sessionsOverTimeMap.containsKey(bucket)) {
                sessionsOverTimeMap.put(bucket, 1);
            } else {
                sessionsOverTimeMap.put(bucket, sessionsOverTimeMap.get(bucket) + 1);
            }
        }

        List<ChildProgressDashboardResponse.ChartPoint> sessionsOverTime = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sessionsOverTimeMap.entrySet()) {
            sessionsOverTime.add(new ChildProgressDashboardResponse.ChartPoint(entry.getKey(), entry.getValue(), null, null));
        }

        // Chart 2: subject breakdown
        Map<String, Integer> subjectMap = new TreeMap<>();
        for (SessionWithTime swt : completed) {

            String subjectName = "Unknown";
            if (swt.session.getSubject() != null && swt.session.getSubject().getName() != null) {
                subjectName = swt.session.getSubject().getName();
            }

            if (!subjectMap.containsKey(subjectName)) {
                subjectMap.put(subjectName, 1);
            } else {
                subjectMap.put(subjectName, subjectMap.get(subjectName) + 1);
            }
        }

        List<ChildProgressDashboardResponse.ChartPoint> subjectBreakdown = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : subjectMap.entrySet()) {
            subjectBreakdown.add(new ChildProgressDashboardResponse.ChartPoint(entry.getKey(), entry.getValue(), null, null));
        }

        // Current subjects (use upcoming sessions)
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

        // Last 3 tutor notes (from completed sessions)
        completed.sort((a, b) -> b.sessionTime.compareTo(a.sessionTime));

        List<ChildProgressDashboardResponse.ChartPoint> lastTutorNotes = new ArrayList<>();
        int count = 0;

        for (SessionWithTime swt : completed) {
            if (count == 3) break;

            String tutorName = "Tutor";
            if (swt.session.getTimeslot() != null
                    && swt.session.getTimeslot().getTutor() != null
                    && swt.session.getTimeslot().getTutor().getName() != null) {
                tutorName = swt.session.getTimeslot().getTutor().getName();
            }

            String note = swt.session.getSessionNotes(); // can be null/blank
            lastTutorNotes.add(new ChildProgressDashboardResponse.ChartPoint(tutorName, 0, swt.sessionTime, note));

            count++;
        }

        return new ChildProgressDashboardResponse(
                childId,
                totalCompletedSessions,
                mostRecentSessionDate,
                sessionsOverTime,
                subjectBreakdown,
                currentSubjects,
                lastTutorNotes
        );
    }

    // -----------------------------
    // DEMO mock dashboard (no DB)
    // -----------------------------
    public ChildProgressDashboardResponse getMockDashboard(Long childId, String groupBy) {

        String grouping = normalizeGroupBy(groupBy);

        // You can make the mock look different per child
        String childLabel = "Child " + childId;

        List<ChildProgressDashboardResponse.ChartPoint> sessionsOverTime = new ArrayList<>();
        List<ChildProgressDashboardResponse.ChartPoint> subjectBreakdown = new ArrayList<>();
        List<ChildProgressDashboardResponse.ChartPoint> currentSubjects = new ArrayList<>();
        List<ChildProgressDashboardResponse.ChartPoint> lastTutorNotes = new ArrayList<>();

        // Sessions over time
        if (grouping.equals("month")) {
            sessionsOverTime.add(new ChildProgressDashboardResponse.ChartPoint("2026-01", 1, null, null));
            sessionsOverTime.add(new ChildProgressDashboardResponse.ChartPoint("2026-02", 3, null, null));
            sessionsOverTime.add(new ChildProgressDashboardResponse.ChartPoint("2026-03", 2, null, null));
        } else {
            sessionsOverTime.add(new ChildProgressDashboardResponse.ChartPoint("2026-W05", 1, null, null));
            sessionsOverTime.add(new ChildProgressDashboardResponse.ChartPoint("2026-W06", 2, null, null));
            sessionsOverTime.add(new ChildProgressDashboardResponse.ChartPoint("2026-W07", 3, null, null));
        }

        // Subject breakdown
        subjectBreakdown.add(new ChildProgressDashboardResponse.ChartPoint("Math", 3, null, null));
        subjectBreakdown.add(new ChildProgressDashboardResponse.ChartPoint("Science", 2, null, null));
        subjectBreakdown.add(new ChildProgressDashboardResponse.ChartPoint("English", 1, null, null));

        // Current subjects (upcoming)
        currentSubjects.add(new ChildProgressDashboardResponse.ChartPoint("Math", 1, null, null));
        currentSubjects.add(new ChildProgressDashboardResponse.ChartPoint("Science", 1, null, null));

        // Notes
        lastTutorNotes.add(new ChildProgressDashboardResponse.ChartPoint(
                "Ava Johnson",
                0,
                LocalDateTime.now().minusDays(2),
                childLabel + ": Improved confidence with fractions."
        ));
        lastTutorNotes.add(new ChildProgressDashboardResponse.ChartPoint(
                "Noah Smith",
                0,
                LocalDateTime.now().minusDays(7),
                childLabel + ": Great participation in science discussion."
        ));
        lastTutorNotes.add(new ChildProgressDashboardResponse.ChartPoint(
                "Mia Davis",
                0,
                LocalDateTime.now().minusDays(10),
                childLabel + ": Reading comprehension improving steadily."
        ));

        int totalCompletedSessions = 6;
        LocalDateTime mostRecentSessionDate = LocalDateTime.now().minusDays(2);

        return new ChildProgressDashboardResponse(
                childId,
                totalCompletedSessions,
                mostRecentSessionDate,
                sessionsOverTime,
                subjectBreakdown,
                currentSubjects,
                lastTutorNotes
        );
    }

    // -----------------------------
    // Helpers
    // -----------------------------
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
}