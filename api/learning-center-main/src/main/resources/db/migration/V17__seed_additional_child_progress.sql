INSERT INTO SESSION.SESSION (session_notes, tutor_time_slot_id, child_id, subject_id, created_at, attended)
WITH

child_weeks AS (
    SELECT c.child_num, w.week_num
    FROM (
        SELECT 1 AS child_num UNION ALL
        SELECT 2 UNION ALL
        SELECT 3 UNION ALL
        SELECT 4
    ) c
    CROSS JOIN (
        SELECT 0 AS week_num UNION ALL
        SELECT 1 UNION ALL
        SELECT 2 UNION ALL
        SELECT 3
    ) w
),

sessions AS (
    SELECT
        child_num,
        week_num,
        DATEADD('DAY', -(week_num * 7 + FLOOR(RANDOM() * 7)), CURRENT_DATE) AS session_day
    FROM child_weeks
),

final_data AS (
    SELECT
        (SELECT child_id FROM PARENT_ACCOUNT.CHILD WHERE child_id BETWEEN 1 AND 8 ORDER BY RANDOM() LIMIT 1)  AS child_id,
        (SELECT subject_id FROM TUTOR_PROFILE.SUBJECT ORDER BY RANDOM() LIMIT 1)                               AS subject_id,
        (SELECT tutor_time_slot_id FROM SESSION.TUTOR_TIME_SLOT ORDER BY RANDOM() LIMIT 1)                     AS tutor_time_slot_id,
        DATEADD('HOUR', 17, CAST(session_day AS TIMESTAMP))                                                  AS created_at,
        (RANDOM() > 0.2)                                                                                        AS attended,
        CASE FLOOR(RANDOM() * 10)
            WHEN 0 THEN 'Needs confidence boost'
            WHEN 1 THEN 'Struggles with fractions'
            WHEN 2 THEN 'Reading comprehension ok'
            WHEN 3 THEN 'Good progress this session'
            WHEN 4 THEN 'Needs more practice at home'
            WHEN 5 THEN 'Great improvement since last week'
            WHEN 6 THEN 'Struggles with core concepts'
            WHEN 7 THEN 'Engaged and motivated today'
            WHEN 8 THEN 'Requires additional support'
            ELSE 'Showing strong understanding'
        END                                                                                                    AS session_notes
    FROM sessions
)

SELECT
    CASE WHEN attended THEN session_notes ELSE NULL END AS session_notes,
    tutor_time_slot_id,
    child_id,
    subject_id,
    created_at,
    attended
FROM final_data;