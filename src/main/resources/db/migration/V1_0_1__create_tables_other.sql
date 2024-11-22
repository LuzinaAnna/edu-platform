CREATE TABLE certification_form(
    id INT PRIMARY KEY,
    form_name VARCHAR(64) NOT NULL UNIQUE
);
CREATE TABLE teacher(
    id INT PRIMARY KEY,
    last_name VARCHAR(64) NOT NULL,
    name VARCHAR(64) NOT NULL,
    patronymic VARCHAR(64)
);
CREATE TABLE "group"(
    id INT PRIMARY KEY,
    group_name VARCHAR(64) NOT NULL UNIQUE,
    education_form_id INT NOT NULL,
    CONSTRAINT education_form_group_fk FOREIGN KEY (education_form_id) REFERENCES education_form(id) ON DELETE CASCADE ON UPDATE CASCADE,
    education_level_id INT NOT NULL,
    CONSTRAINT education_level_group_fk FOREIGN KEY (education_level_id) REFERENCES education_level(id) ON DELETE CASCADE ON UPDATE CASCADE,
    department_id INT NOT NULL,
    CONSTRAINT department_group_fk FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE CASCADE ON UPDATE CASCADE,
    curator_id INT NOT NULL,
    CONSTRAINT curator_group_fk FOREIGN KEY (curator_id) REFERENCES teacher(id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE student(
    id INT PRIMARY KEY,
    last_name VARCHAR(64) NOT NULL,
    name VARCHAR(64) NOT NULL,
    patronymic VARCHAR(64),
    group_id INT NOT NULL,
    CONSTRAINT group_student_fk FOREIGN KEY (group_id) REFERENCES "group"(id) ON DELETE CASCADE ON UPDATE CASCADE,
    birthday DATE NOT NULL
);
CREATE TABLE subject_taught(
    id INT PRIMARY KEY,
    education_period_id INT NOT NULL,
    CONSTRAINT education_period_subject_taught_fk FOREIGN KEY (education_period_id) REFERENCES education_period(id) ON DELETE CASCADE ON UPDATE CASCADE,
    teacher_id INT NOT NULL,
    CONSTRAINT teacher_subject_taught_fk FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE ON UPDATE CASCADE,
    discipline_id INT NOT NULL,
    CONSTRAINT discipline_subject_taught_fk FOREIGN KEY (discipline_id) REFERENCES discipline(id) ON DELETE CASCADE ON UPDATE CASCADE,
    group_id INT NOT NULL,
    CONSTRAINT group_subject_taught_fk FOREIGN KEY (group_id) REFERENCES "group"(id) ON DELETE CASCADE ON UPDATE CASCADE,
    teaching_type_id INT NOT NULL,
    CONSTRAINT teaching_type_subject_taught_fk FOREIGN KEY (teaching_type_id) REFERENCES teaching_type(id) ON DELETE CASCADE ON UPDATE CASCADE,
    certification_form_id INT NOT NULL,
    CONSTRAINT certification_form_subject_taught_fk FOREIGN KEY (certification_form_id) REFERENCES certification_form(id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE subject_taught_mark(
    id INT PRIMARY KEY,
    subject_taught_id INT NOT NULL,
    CONSTRAINT subject_taught_subject_taught_mark_fk FOREIGN KEY (subject_taught_id) REFERENCES subject_taught(id) ON DELETE CASCADE ON UPDATE CASCADE,
    student_id INT NOT NULL,
    CONSTRAINT student_subject_taught_mark_fk FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE ON UPDATE CASCADE,
    mark_id INT NOT NULL,
    CONSTRAINT mark_taught_mark_fk FOREIGN KEY (mark_id) REFERENCES mark(id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE task(
    id INT PRIMARY KEY,
    description_task TEXT,
    type_task_id INT NOT NULL,
    CONSTRAINT type_task_task_fk FOREIGN KEY (type_task_id) REFERENCES type_task(id) ON DELETE CASCADE ON UPDATE CASCADE,
    subject_taught_id INT NOT NULL,
    CONSTRAINT subject_taught_task_fk FOREIGN KEY (subject_taught_id) REFERENCES subject_taught(id) ON DELETE CASCADE ON UPDATE CASCADE,
    start_date DATE NOT NULL,
    deadline DATE NOT NULL
);
CREATE TABLE progress(
    id INT PRIMARY KEY,
    task_id INT NOT NULL,
    CONSTRAINT task_progress_fk FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE ON UPDATE CASCADE,
    mark_id INT NOT NULL,
    CONSTRAINT mark_progress_fk FOREIGN KEY (mark_id) REFERENCES mark(id) ON DELETE CASCADE ON UPDATE CASCADE,
    student_id INT NOT NULL,
    CONSTRAINT student_progress_fk FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE ON UPDATE CASCADE,
    actual_date DATE NOT NULL
);
CREATE TABLE schedule(
    id INT PRIMARY KEY,
    start_of_counting DATE NOT NULL,
    subject_taught_id INT NOT NULL,
    CONSTRAINT subject_taught_schedule_fk FOREIGN KEY (subject_taught_id) REFERENCES subject_taught(id) ON DELETE CASCADE ON UPDATE CASCADE,
    period_type_id INT NOT NULL,
    CONSTRAINT period_type_schedule_fk FOREIGN KEY (period_type_id) REFERENCES period_type(id) ON DELETE CASCADE ON UPDATE CASCADE,
    classroom_id INT NOT NULL,
    CONSTRAINT classroom_schedule_fk FOREIGN KEY (classroom_id) REFERENCES classroom(id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE visit(
    id INT PRIMARY KEY,
    schedule_id INT NOT NULL,
    CONSTRAINT schedule_visit_fk FOREIGN KEY (schedule_id) REFERENCES schedule(id) ON DELETE CASCADE ON UPDATE CASCADE,
    student_id INT NOT NULL,
    CONSTRAINT student_visit_fk FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE ON UPDATE CASCADE,
    datetime_visit TIMESTAMP NOT NULL
);
CREATE TABLE topic_of_lesson(
    id INT PRIMARY KEY,
    datetime_lesson TIMESTAMP NOT NULL,
    name_of_topic VARCHAR(64) NOT NULL,
    schedule_id INT NOT NULL,
    CONSTRAINT schedule_topic_of_lesson_fk FOREIGN KEY (schedule_id) REFERENCES schedule(id) ON DELETE CASCADE ON UPDATE CASCADE
);