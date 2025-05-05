-- User management tables
CREATE TABLE job_title
(
    id       bigint PRIMARY KEY,
    job_name varchar(64) NOT NULL,
    CONSTRAINT uq_job_title UNIQUE (job_name)
);

CREATE TABLE teacher
(
    id                  bigint PRIMARY KEY,
    last_name           varchar(64) NOT NULL,
    name                varchar(64) NOT NULL,
    patronymic          varchar(64),
    job_title_id        bigint      NOT NULL,
    department_id       bigint      NOT NULL,
    security_subject_id bigint      NOT NULL,
    email               varchar(128),
    phone               varchar(20),
    hire_date           date,
    created_at          timestamp DEFAULT now(),
    CONSTRAINT fk_teacher_job_title FOREIGN KEY (job_title_id) REFERENCES job_title (id),
    CONSTRAINT fk_teacher_department FOREIGN KEY (department_id) REFERENCES department (id),
    CONSTRAINT fk_teacher_security_subject FOREIGN KEY (security_subject_id) REFERENCES security_subject (id),
    CONSTRAINT uq_teacher_email UNIQUE (email)
);
CREATE INDEX idx_teacher_name ON teacher (last_name, name);

-- Add faculty dean reference after teacher table exists
ALTER TABLE faculty
    ADD CONSTRAINT fk_faculty_dean
        FOREIGN KEY (dean_id) REFERENCES teacher (id);

-- Add department head reference after teacher table exists
ALTER TABLE department
    ADD CONSTRAINT fk_department_head
        FOREIGN KEY (head_id) REFERENCES teacher (id);

CREATE TABLE "group"
(
    id                 bigint PRIMARY KEY,
    group_name         varchar(64) NOT NULL,
    education_form_id  bigint      NOT NULL,
    education_level_id bigint      NOT NULL,
    department_id      bigint      NOT NULL,
    curator_id         bigint      NOT NULL,
    start_year         integer     NOT NULL,
    end_year           integer     NOT NULL,
    is_active          boolean   DEFAULT true,
    created_at         timestamp DEFAULT now(),
    CONSTRAINT fk_group_education_form FOREIGN KEY (education_form_id) REFERENCES education_form (id),
    CONSTRAINT fk_group_education_level FOREIGN KEY (education_level_id) REFERENCES education_level (id),
    CONSTRAINT fk_group_department FOREIGN KEY (department_id) REFERENCES department (id),
    CONSTRAINT fk_group_curator FOREIGN KEY (curator_id) REFERENCES teacher (id),
    CONSTRAINT uq_group_name UNIQUE (group_name)
);

CREATE TABLE student
(
    id                  bigint PRIMARY KEY,
    last_name           varchar(64) NOT NULL,
    name                varchar(64) NOT NULL,
    patronymic          varchar(64),
    group_id            bigint      NOT NULL,
    security_subject_id bigint      NOT NULL,
    birthday            date,
    email               varchar(128),
    phone               varchar(20),
    admission_date      date        NOT NULL,
    is_active           boolean   DEFAULT true,
    student_id_number   varchar(32) NOT NULL,
    created_at          timestamp DEFAULT now(),
    CONSTRAINT fk_student_group FOREIGN KEY (group_id) REFERENCES "group" (id),
    CONSTRAINT fk_student_security_subject FOREIGN KEY (security_subject_id) REFERENCES security_subject(id),
    CONSTRAINT uq_student_email UNIQUE (email),
    CONSTRAINT uq_student_id_number UNIQUE (student_id_number)
);
CREATE INDEX idx_student_name ON student (last_name, name);