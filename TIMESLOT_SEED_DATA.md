# Tutor Timeslot Seed Data

## Overview

Created seed data for tutor availability timeslots for the next 3 months (March 9 - June 5, 2026).

## Data Specifications

### Duration

- **Start Date:** March 9, 2026 (Monday)
- **End Date:** June 5, 2026 (Friday)
- **Duration:** 13 weeks (3 months)
- **Business Days Only:** Monday - Friday

### Time Slots

- **Operating Hours:** 2:00 PM - 6:00 PM (2 PM, 3 PM, 4 PM, 5 PM, 6 PM)
- **Duration Per Slot:** 1 hour
- **Slots Per Day:** 5 hours

### Coverage

- **Total Business Days:** 65 days
- **Timeslots Created:** 325 unique timeslots (65 days × 5 hours)
- **Tutors Assigned:** 15 tutors (all tutors in the system)
- **Total Tutor-Timeslot Records:** 4,875 (325 × 15 tutors)

### Tutors Included

All 15 tutors from the seed data are assigned these timeslots:

1. Ava Johnson
2. Noah Smith
3. Mia Davis
4. Ethan Brown
5. Sophia Wilson
6. Liam Martinez
7. Olivia Taylor
8. James Anderson
9. Isabella Thomas
10. Benjamin Moore
11. Charlotte Lee
12. Lucas Harris
13. Amelia Clark
14. Henry Lewis
15. Harper Young

## Database Location

- **Migration File:** `V9__seed_tutor_timeslots.sql`
- **Schema:** `session.time_slot` and `session.tutor_time_slot`
- **Table Names:**
  - `session.time_slot` - Individual available time slots
  - `session.tutor_time_slot` - Many-to-many relationship between tutors and timeslots

## Purpose

- **For Parents:** Can browse and select available sessions from tutors for their children
- **For Tutors:** Display their availability to parents and track booked sessions
- **For System:** Allow filtering and searching for available tutoring sessions by date/time

## Load Test Results

- Successfully executed via Flyway migration
- Verified by `TutorRepositoryIntegrationTest` (9 tests passed)
- Integration with existing tutor, parent, and session schemas confirmed

## Sample Queries

### Get all available timeslots for a tutor

```sql
SELECT ts.time_slot_id, ts.time
FROM session.time_slot ts
JOIN session.tutor_time_slot tts ON ts.time_slot_id = tts.time_slot_id
WHERE tts.tutor_id = <tutor_id>
ORDER BY ts.time ASC;
```

### Get available tutors for a specific time and subject

```sql
SELECT t.tutor_id, t.name, t.summary
FROM tutor_profile.tutor t
JOIN tutor_profile.tutor_subject ts ON t.tutor_id = ts.tutor_id
JOIN tutor_profile.subject s ON ts.subject_id = s.subject_id
JOIN session.tutor_time_slot tts ON t.tutor_id = tts.tutor_id
JOIN session.time_slot slot ON tts.time_slot_id = slot.time_slot_id
WHERE s.subject_name = '<subject>'
  AND slot.time = '<timestamp>'
  AND t.min_grade_level <= <child_grade>
  AND t.max_grade_level >= <child_grade>;
```

## Notes

- Timeslots are available for immediate use
- Matches default timeslot generation settings (Mon-Fri, 2-6 PM)
- Can be extended by running the TimeslotGeneratorService for specific tutors
- Booked sessions will remove timeslots from availability
