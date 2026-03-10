-- Seed goals for Child A using existing subjects
INSERT INTO parent_account.goal (child_id, subject_id, title)
SELECT c.child_id, s.subject_id, 'Improve science confidence'
FROM parent_account.child c
         JOIN tutor_profile.subject s
              ON s.subject_name = 'Science'
WHERE c.name = 'Child A';

INSERT INTO parent_account.goal (child_id, subject_id, title)
SELECT c.child_id, s.subject_id, 'Strengthen reading comprehension'
FROM parent_account.child c
         JOIN tutor_profile.subject s
              ON s.subject_name = 'English'
WHERE c.name = 'Child A';

-- Seed progress tied to existing sessions
INSERT INTO session.progress (goal_id, percentage_complete, session_id)
SELECT g.goal_id, 35, ss.session_id
FROM parent_account.goal g
         JOIN parent_account.child c
              ON c.child_id = g.child_id
         JOIN tutor_profile.subject sub
              ON sub.subject_id = g.subject_id
         JOIN session.session ss
              ON ss.child_id = c.child_id
                  AND ss.subject_id = sub.subject_id
WHERE c.name = 'Child A'
  AND g.title = 'Improve science confidence';

INSERT INTO session.progress (goal_id, percentage_complete, session_id)
SELECT g.goal_id, 70, ss.session_id
FROM parent_account.goal g
         JOIN parent_account.child c
              ON c.child_id = g.child_id
         JOIN tutor_profile.subject sub
              ON sub.subject_id = g.subject_id
         JOIN session.session ss
              ON ss.child_id = c.child_id
                  AND ss.subject_id = sub.subject_id
WHERE c.name = 'Child A'
  AND g.title = 'Strengthen reading comprehension';