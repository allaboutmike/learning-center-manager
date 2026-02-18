CREATE SCHEMA IF NOT EXISTS tutor_profile;


create table tutor_profile.tutor
(
    tutor_id         BIGINT AUTO_INCREMENT,
    url              VARCHAR(255) not null,
    name             VARCHAR(50) not null,
    summary          VARCHAR(200) not null,
    min_grade_level  INTEGER not null,
    max_grade_level  INTEGER not null,
    constraint tutor_pk
        primary key (tutor_id)

);

create table tutor_profile.subject
(
    subject_id   BIGINT not null AUTO_INCREMENT,
    subject_name VARCHAR(50) not null,
    constraint subject_pk
        primary key (subject_id)
);


create table tutor_profile.tutor_subject
(
    tutor_id         BIGINT not null,
    subject_id       BIGINT not null,
    constraint tutor_subject_pk
        primary key (subject_id, tutor_id),
    constraint subject_id__fk
        foreign key (subject_id) references TUTOR_PROFILE.SUBJECT (subject_id),
    constraint tutor_id__fk
        foreign key (tutor_id) references TUTOR_PROFILE.TUTOR (tutor_id)
);
-- ##############  Code Review table here  #################
CREATE SCHEMA IF NOT EXISTS review;

create table review.review
(
    review_id  BIGINT AUTO_INCREMENT,
    tutor_id   BIGINT not null,
    rating     INTEGER not null check (rating >= 0 and rating <= 5),
    comment    VARCHAR(255) not null,
    constraint review_pk
        primary key (review_id),
    constraint tutor_id__fk
        foreign key (tutor_id) references TUTOR_PROFILE.TUTOR (tutor_id)
);

-- ##############################################################



CREATE SCHEMA IF NOT EXISTS parent_account;

-- ##############  Code for parent and child table here  #################
create table parent_account.parent
(
    parent_id   BIGINT AUTO_INCREMENT,
    name        VARCHAR(50) not null,
    constraint parent_pk
        primary key (parent_id)
);

create table parent_account.child
(
    child_id   BIGINT AUTO_INCREMENT,
    parent_id  BIGINT not null,
    name       VARCHAR(50) not null,
    grade_level INTEGER not null,
    constraint child_pk
        primary key (child_id),
    constraint parent_id__fk
        foreign key (parent_id) references PARENT_ACCOUNT.PARENT (parent_id)
);

-- ##############################################################


CREATE SCHEMA IF NOT EXISTS session;

-- ##############  Code for session, time slot, and tutor timeslot table here  #################
create table session.time_slot
(
    time_slot_id BIGINT not null AUTO_INCREMENT,
    time  TIMESTAMP not null,
    constraint time_slot_pk
        primary key (time_slot_id)
);

create table session.tutor_time_slot
(
    tutor_time_slot_id BIGINT not null AUTO_INCREMENT,
    tutor_id          BIGINT not null,
    time_slot_id      BIGINT not null,
    constraint tutor_time_slot_pk
        primary key (tutor_time_slot_id),
    constraint tutor_id__fk
        foreign key (tutor_id) references TUTOR_PROFILE.TUTOR (tutor_id),
    constraint time_slot_id__fk
        foreign key (time_slot_id) references SESSION.TIME_SLOT (time_slot_id)
);

create table session.session
(
    session_id        BIGINT AUTO_INCREMENT,
    session_notes     VARCHAR(255) null,
    tutor_time_slot_id BIGINT not null,
    child_id          BIGINT not null,
    subject_id        BIGINT not null,
    created_at         TIMESTAMP not null,
    constraint session_pk
        primary key (session_id),
    constraint tutor_time_slot_id__fk
        foreign key (tutor_time_slot_id) references SESSION.TUTOR_TIME_SLOT (tutor_time_slot_id),
    constraint child_id__fk
        foreign key (child_id) references PARENT_ACCOUNT.CHILD (child_id),
    constraint subject_id__fk
        foreign key (subject_id) references TUTOR_PROFILE.SUBJECT (subject_id)
);
