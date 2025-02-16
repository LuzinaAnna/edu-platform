CREATE TABLE security_subject
(
    id             bigint primary key,
    username       varchar(512),
    pwd            varchar(512),
    email          varchar(512),
    is_expired     smallint not null default 0,
    is_locked      smallint not null default 0,
    is_pwd_expired smallint not null default 0,
    is_enabled     smallint not null default 0
);

CREATE TABLE security_grant
(
    id   bigint primary key,
    name varchar(512)
);

CREATE TABLE subject_grant
(
    subject_id bigint,
    grant_id   bigint,
    constraint subject_grants_security_grant_fk foreign key (grant_id) references security_grant (id),
    constraint subject_grants_security_subject_fk foreign key (subject_id) references security_subject (id)
);


CREATE TABLE subject_to_student
(
    student_id bigint NOT NULL,
    subject_id bigint NOT NULL,
    constraint subject_to_student_student_id_fk foreign key (student_id) references student (id),
    constraint subject_to_student_subject_id_fk foreign key (student_id) references security_subject (id)
);

CREATE SEQUENCE security_subject_id_seq;
CREATE SEQUENCE security_grant_id_seq;


insert into security_grant(id, name)
values (nextval('security_grant_id_seq'), 'ADMIN'),
       (nextval('security_grant_id_seq'), 'STUDENT');