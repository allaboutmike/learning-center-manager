create table tutor_profile.subject
    (
        subject_id   int         not null
            primary key,
        subject_name varchar(50) not null
    );

create table tutor_profile.tutor
(
    tutor_id        bigint auto_increment
        primary key,
    url             varchar(255) not null,
    name            varchar(50)  not null,
    summary         varchar(255) not null,
    min_grade_level int          not null,
    max_grade_level int          not null
);

create table tutor_profile.review
(
    review_id      int          not null,
    stars          decimal      not null,
    review_comment varchar(255) null,
    tutor_id       bigint       not null,
    constraint review_tutor_id__fk
        foreign key (tutor_id) references tutor (tutor_id)
);

create table tutor_profile.tutor_subject
(
    tutor_subject_id int not null
        primary key,
    tutor_id         int not null,
    subject_id       int not null,
    constraint tutor_subject_id___fk
        foreign key (subject_id) references subject (subject_id)
);

