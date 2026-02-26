-- Demo seed, mocke session data for each clild that the parent has. 

-- SESSIONS
INSERT INTO session.session (child_id, subject_id, tutor_time_slot_id, session_notes, created_at)
SELECT
    c.child_id,
    s.subject_id,
    tts.tutor_time_slot_id,
    v.session_notes,
    v.time
FROM (VALUES 
    ('Child A', 'Science', 'Needs confidence boost',      TIMESTAMP '2026-02-16 17:00:00'),
    ('Child B', 'Math',    'Trouble with word problems',  TIMESTAMP '2026-02-15 13:00:00'),
    ('Child C', 'Spanish', 'Good progress so far',        TIMESTAMP '2026-02-16 17:00:00'),
    ('Child D', 'Math',    'Struggles with fractions',    TIMESTAMP '2026-02-16 17:00:00'),
    ('Child A', 'English', 'Reading comprehension ok',    TIMESTAMP '2026-02-17 17:00:00')
) AS v(child_name, subject_name, session_notes, time)

JOIN parent_account.child c 
    ON c.name = v.child_name

JOIN tutor_profile.subject s
    ON s.subject_name = v.subject_name

JOIN session.time_slot ts
    ON ts.time = v.time

JOIN session.tutor_time_slot tts
    ON tts.time_slot_id = ts.time_slot_id
    JOIN tutor_profile.tutor_subject tsub
        ON tsub.tutor_id = tts.tutor_id
       AND tsub.subject_id = s.subject_id;