-- Demo seed, auto generated IDs, no sessions created.

-- SUBJECTS (8)
INSERT INTO tutor_profile.subject (subject_name) VALUES
                                                     ('Math'),
                                                     ('Science'),
                                                     ('English'),
                                                     ('History'),
                                                     ('Computer Science'),
                                                     ('Spanish'),
                                                     ('SAT Prep'),
                                                     ('Algebra');

-- TUTORS (15)
INSERT INTO tutor_profile.tutor (url, name, summary, min_grade_level, max_grade_level) VALUES
    ('https://i.pinimg.com/564x/50/14/73/501473de7c8bb84e7e21d6ffa2e96d45.jpg',  'Ava Johnson',     'Patient tutor focused on strong foundations.', 3,  8),
    ('https://avatarfiles.alphacoders.com/378/thumb-1920-378248.webp',  'Noah Smith',      'Science tutor who keeps lessons practical.',   5, 10),
    ('https://example.com/tutors/3',  'Mia Davis',       'Reading and writing support with structure.',  2,  7),
    ('https://example.com/tutors/4',  'Ethan Brown',     'History tutor with clear explanations.',       6, 12),
    ('https://example.com/tutors/5',  'Sophia Wilson',   'CS tutor for beginners and projects.',         8, 12),
    ('https://example.com/tutors/6',  'Liam Martinez',   'Algebra tutor with step by step approach.',    6, 10),
    ('https://example.com/tutors/7',  'Olivia Taylor',   'Spanish tutor for conversation and grammar.',  4,  9),
    ('https://example.com/tutors/8',  'James Anderson',  'SAT prep tutor focused on strategies.',        9, 12),
    ('https://example.com/tutors/9',  'Isabella Thomas', 'Math and science tutor, strong coaching.',     7, 12),
    ('https://example.com/tutors/10', 'Benjamin Moore',  'English and history tutor, structured plans.', 5, 11),
    ('https://example.com/tutors/11', 'Charlotte Lee',   'Science tutor for concepts and practice.',     6, 12),
    ('https://example.com/tutors/12', 'Lucas Harris',    'Math tutor with exam preparation focus.',      8, 12),
    ('https://example.com/tutors/13', 'Amelia Clark',    'Early reading tutor, confidence builder.',     1,  5),
    ('https://example.com/tutors/14', 'Henry Lewis',     'CS tutor for problem solving and basics.',     9, 12),
    ('https://example.com/tutors/15', 'Harper Young',    'Spanish and English tutor, supportive style.', 3,  8);


-- TUTOR_SUBJECT (many-to-many)
INSERT INTO tutor_profile.tutor_subject (tutor_id, subject_id)
SELECT t.tutor_id, s.subject_id
FROM (
         VALUES
             ('Ava Johnson',     'Math'),
             ('Ava Johnson',     'Algebra'),
             ('Noah Smith',      'Science'),
             ('Noah Smith',      'Math'),
             ('Mia Davis',       'English'),
             ('Mia Davis',       'Math'),
             ('Ethan Brown',     'History'),
             ('Ethan Brown',     'English'),
             ('Sophia Wilson',   'Computer Science'),
             ('Sophia Wilson',   'SAT Prep'),
             ('Liam Martinez',   'Algebra'),
             ('Liam Martinez',   'Math'),
             ('Olivia Taylor',   'Spanish'),
             ('Olivia Taylor',   'English'),
             ('James Anderson',  'SAT Prep'),
             ('James Anderson',  'Math'),
             ('Isabella Thomas', 'Math'),
             ('Isabella Thomas', 'Science'),
             ('Isabella Thomas', 'Algebra'),
             ('Benjamin Moore',  'English'),
             ('Benjamin Moore',  'History'),
             ('Charlotte Lee',   'Science'),
             ('Charlotte Lee',   'SAT Prep'),
             ('Lucas Harris',    'Math'),
             ('Lucas Harris',    'Algebra'),
             ('Lucas Harris',    'SAT Prep'),
             ('Amelia Clark',    'English'),
             ('Henry Lewis',     'Computer Science'),
             ('Henry Lewis',     'Math'),
             ('Harper Young',    'Spanish'),
             ('Harper Young',    'English')
     ) AS m(tutor_name, subject_name)
         JOIN tutor_profile.tutor t ON t.name = m.tutor_name
         JOIN tutor_profile.subject s ON s.subject_name = m.subject_name;

-- REVIEWS (12)
INSERT INTO review.review (tutor_id, rating, comment)
SELECT t.tutor_id, r.rating, r.comment
FROM (
         VALUES
             ('Ava Johnson',     5, 'Very clear explanations.'),
             ('Ava Johnson',     4, 'Helpful and patient.'),
             ('Noah Smith',      5, 'Great science examples.'),
             ('Mia Davis',       4, 'Improved writing confidence.'),
             ('Ethan Brown',     4, 'Well prepared and organized.'),
             ('Sophia Wilson',   5, 'Great for beginner projects.'),
             ('Liam Martinez',   4, 'Strong algebra support.'),
             ('Olivia Taylor',   5, 'Great conversation practice.'),
             ('James Anderson',  4, 'Good SAT strategies.'),
             ('Isabella Thomas', 5, 'Coaching style is excellent.'),
             ('Benjamin Moore',  4, 'Clear structure and guidance.'),
             ('Lucas Harris',    5, 'Excellent exam prep.'),
             ('Amelia Clark',    5, 'Helped my child love reading.'),
             ('Henry Lewis',     4, 'Good for CS basics.'),
             ('Harper Young',    5, 'Supportive and effective.'),
             ('Charlotte Lee',   4, 'Great science tutor.')
     ) AS r(tutor_name, rating, comment)
         JOIN tutor_profile.tutor t ON t.name = r.tutor_name;

-- PARENTS (2) and CHILDREN (4)
INSERT INTO parent_account.parent (name) VALUES
                                             ('Parent One'),
                                             ('Parent Two');

INSERT INTO parent_account.child (parent_id, name, grade_level)
SELECT p.parent_id, c.child_name, c.grade_level
FROM (
         VALUES
             ('Parent One', 'Child A', 5),
             ('Parent Two', 'Child B', 3),
             ('Parent Two', 'Child C', 7),
             ('Parent Two', 'Child D', 9)
     ) AS c(parent_name, child_name, grade_level)
         JOIN parent_account.parent p ON p.name = c.parent_name;

-- TIME SLOTS (20)
INSERT INTO session.time_slot (time) VALUES
                                         (TIMESTAMP '2026-02-16 16:00:00'),
                                         (TIMESTAMP '2026-02-16 17:00:00'),
                                         (TIMESTAMP '2026-02-16 18:00:00'),
                                         (TIMESTAMP '2026-02-17 16:00:00'),
                                         (TIMESTAMP '2026-02-17 17:00:00'),
                                         (TIMESTAMP '2026-02-17 18:00:00'),
                                         (TIMESTAMP '2026-02-18 16:00:00'),
                                         (TIMESTAMP '2026-02-18 17:00:00'),
                                         (TIMESTAMP '2026-02-18 18:00:00'),
                                         (TIMESTAMP '2026-02-19 16:00:00'),
                                         (TIMESTAMP '2026-02-19 17:00:00'),
                                         (TIMESTAMP '2026-02-19 18:00:00'),
                                         (TIMESTAMP '2026-02-20 16:00:00'),
                                         (TIMESTAMP '2026-02-20 17:00:00'),
                                         (TIMESTAMP '2026-02-20 18:00:00'),
                                         (TIMESTAMP '2026-02-21 10:00:00'),
                                         (TIMESTAMP '2026-02-21 11:00:00'),
                                         (TIMESTAMP '2026-02-21 12:00:00'),
                                         (TIMESTAMP '2026-02-22 14:00:00'),
                                         (TIMESTAMP '2026-02-22 15:00:00');

-- TUTOR_TIME_SLOT (25)
INSERT INTO session.tutor_time_slot (tutor_id, time_slot_id)
SELECT t.tutor_id, ts.time_slot_id
FROM (
         VALUES
             ('Ava Johnson',     TIMESTAMP '2026-02-16 16:00:00'),
             ('Ava Johnson',     TIMESTAMP '2026-02-17 16:00:00'),
             ('Noah Smith',      TIMESTAMP '2026-02-16 17:00:00'),
             ('Noah Smith',      TIMESTAMP '2026-02-17 17:00:00'),
             ('Mia Davis',       TIMESTAMP '2026-02-16 18:00:00'),
             ('Mia Davis',       TIMESTAMP '2026-02-17 18:00:00'),
             ('Ethan Brown',     TIMESTAMP '2026-02-18 16:00:00'),
             ('Ethan Brown',     TIMESTAMP '2026-02-19 16:00:00'),
             ('Sophia Wilson',   TIMESTAMP '2026-02-18 17:00:00'),
             ('Sophia Wilson',   TIMESTAMP '2026-02-19 17:00:00'),
             ('Liam Martinez',   TIMESTAMP '2026-02-18 18:00:00'),
             ('Liam Martinez',   TIMESTAMP '2026-02-19 18:00:00'),
             ('Olivia Taylor',   TIMESTAMP '2026-02-20 16:00:00'),
             ('Olivia Taylor',   TIMESTAMP '2026-02-21 10:00:00'),
             ('James Anderson',  TIMESTAMP '2026-02-20 17:00:00'),
             ('James Anderson',  TIMESTAMP '2026-02-21 11:00:00'),
             ('Isabella Thomas', TIMESTAMP '2026-02-20 18:00:00'),
             ('Isabella Thomas', TIMESTAMP '2026-02-21 12:00:00'),
             ('Benjamin Moore',  TIMESTAMP '2026-02-22 14:00:00'),
             ('Charlotte Lee',   TIMESTAMP '2026-02-22 15:00:00'),
             ('Lucas Harris',    TIMESTAMP '2026-02-16 16:00:00'),
             ('Lucas Harris',    TIMESTAMP '2026-02-16 17:00:00'),
             ('Henry Lewis',     TIMESTAMP '2026-02-17 16:00:00'),
             ('Harper Young',    TIMESTAMP '2026-02-17 17:00:00'),
             ('Amelia Clark',    TIMESTAMP '2026-02-21 10:00:00')
     ) AS a(tutor_name, slot_time)
         JOIN tutor_profile.tutor t ON t.name = a.tutor_name
         JOIN session.time_slot ts ON ts.time = a.slot_time;

-- Intentionally no inserts into session.session.
