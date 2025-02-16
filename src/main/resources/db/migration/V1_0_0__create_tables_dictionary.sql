CREATE TABLE campus
(
    id          BIGINT PRIMARY KEY,
    campus_name VARCHAR(32),
    address     VARCHAR(64) NOT NULL
);
CREATE TABLE classroom
(
    id        BIGINT PRIMARY KEY,
    number    BIGINT NOT NULL,
    campus_id BIGINT NOT NULL,
    CONSTRAINT campus_classroom_fk FOREIGN KEY (campus_id) REFERENCES campus (id) ON DELETE CASCADE
);
CREATE TABLE period_type
(
    id          BIGINT PRIMARY KEY,
    period_name VARCHAR(64) NOT NULL
);
CREATE TABLE mark
(
    id        BIGINT PRIMARY KEY,
    type_mark VARCHAR(64) NOT NULL
);
CREATE TABLE type_task
(
    id        BIGINT PRIMARY KEY,
    task_name VARCHAR(64) NOT NULL
);
CREATE TABLE education_period
(
    id          BIGINT PRIMARY KEY,
    "from"      DATE        NOT NULL,
    "to"        DATE        NOT NULL,
    period_name VARCHAR(64) NOT NULL
);
CREATE TABLE teaching_type
(
    id            BIGINT PRIMARY KEY,
    teaching_name VARCHAR(64) NOT NULL
);
CREATE TABLE job_title
(
    id       BIGINT PRIMARY KEY,
    job_name VARCHAR(64) NOT NULL
);
CREATE TABLE faculty
(
    id           BIGINT PRIMARY KEY,
    faculty_name VARCHAR(64) NOT NULL
);
CREATE TABLE education_level
(
    id         BIGINT PRIMARY KEY,
    level_name VARCHAR(64) NOT NULL
);
CREATE TABLE education_form
(
    id        BIGINT PRIMARY KEY,
    form_name VARCHAR(64) NOT NULL
);
CREATE TABLE department
(
    id              BIGINT PRIMARY KEY,
    department_name VARCHAR(64) NOT NULL,
    faculty_id      BIGINT      NOT NULL,
    CONSTRAINT faculty_department_fk FOREIGN KEY (faculty_id) REFERENCES faculty (id) ON DELETE CASCADE
);
CREATE TABLE discipline
(
    id              BIGINT PRIMARY KEY,
    discipline_name VARCHAR(64) NOT NULL
);