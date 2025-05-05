CREATE TABLE certification_form (
                                    id bigint PRIMARY KEY,
                                    form_name varchar(64) NOT NULL,
                                    CONSTRAINT uq_certification_form UNIQUE (form_name)
);

CREATE TABLE teaching_type (
                               id bigint PRIMARY KEY,
                               teaching_name varchar(64) NOT NULL,
                               CONSTRAINT uq_teaching_type UNIQUE (teaching_name)
);

CREATE TABLE course (
                        id bigint PRIMARY KEY,
                        semester_id bigint NOT NULL,
                        teacher_id bigint NOT NULL,
                        discipline_id bigint NOT NULL,
                        group_id bigint NOT NULL,
                        teaching_type_id bigint NOT NULL,
                        certification_form_id bigint NOT NULL,
                        classroom_id bigint,
                        credits numeric(3,1) NOT NULL,
                        max_students integer,
                        is_active boolean DEFAULT true,
                        created_at timestamp DEFAULT now(),
                        CONSTRAINT fk_course_semester FOREIGN KEY (semester_id) REFERENCES semester(id),
                        CONSTRAINT fk_course_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(id),
                        CONSTRAINT fk_course_discipline FOREIGN KEY (discipline_id) REFERENCES discipline(id),
                        CONSTRAINT fk_course_group FOREIGN KEY (group_id) REFERENCES "group"(id),
                        CONSTRAINT fk_course_teaching_type FOREIGN KEY (teaching_type_id) REFERENCES teaching_type(id),
                        CONSTRAINT fk_course_certification_form FOREIGN KEY (certification_form_id) REFERENCES certification_form(id),
                        CONSTRAINT fk_course_classroom FOREIGN KEY (classroom_id) REFERENCES classroom(id),
                        CONSTRAINT uq_course_combination UNIQUE (semester_id, discipline_id, group_id, teacher_id)
);

CREATE TABLE mark (
                      id bigint PRIMARY KEY,
                      type_mark varchar(64) NOT NULL,
                      numeric_value numeric(3,1),
                      is_pass boolean,
                      CONSTRAINT uq_mark_type UNIQUE (type_mark)
);

CREATE TABLE type_task (
                           id bigint PRIMARY KEY,
                           task_name varchar(64) NOT NULL,
                           weight numeric(3,2) DEFAULT 1,
                           CONSTRAINT uq_task_type UNIQUE (task_name)
);

CREATE TABLE assignment (
                            id bigint PRIMARY KEY,
                            description text NOT NULL,
                            type_task_id bigint NOT NULL,
                            course_id bigint NOT NULL,
                            start_date date NOT NULL,
                            deadline date NOT NULL,
                            max_score numeric(5,2) NOT NULL,
                            is_published boolean DEFAULT false,
                            created_at timestamp DEFAULT now(),
                            CONSTRAINT fk_assignment_type FOREIGN KEY (type_task_id) REFERENCES type_task(id),
                            CONSTRAINT fk_assignment_course FOREIGN KEY (course_id) REFERENCES course(id)
);
CREATE INDEX idx_assignment_course ON assignment (course_id);

CREATE TABLE grade (
                       id bigint PRIMARY KEY,
                       assignment_id bigint NOT NULL,
                       mark_id bigint NOT NULL,
                       student_id bigint NOT NULL,
                       submission_date timestamp,
                       graded_date timestamp,
                       feedback text,
                       created_at timestamp DEFAULT now(),
                       CONSTRAINT fk_grade_assignment FOREIGN KEY (assignment_id) REFERENCES assignment(id),
                       CONSTRAINT fk_grade_mark FOREIGN KEY (mark_id) REFERENCES mark(id),
                       CONSTRAINT fk_grade_student FOREIGN KEY (student_id) REFERENCES student(id),
                       CONSTRAINT uq_grade_submission UNIQUE (assignment_id, student_id)
);
CREATE INDEX idx_grade_student ON grade (student_id);