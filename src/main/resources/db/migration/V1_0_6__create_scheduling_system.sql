-- Scheduling system
CREATE TABLE schedule (
                          id bigint PRIMARY KEY,
                          course_id bigint NOT NULL,
                          period_type_id bigint NOT NULL,
                          classroom_id bigint,
                          day_of_week smallint NOT NULL,
                          recurring_pattern varchar(10) DEFAULT 'weekly',
                          start_date date NOT NULL,
                          end_date date NOT NULL,
                          created_at timestamp DEFAULT now(),
                          CONSTRAINT fk_schedule_course FOREIGN KEY (course_id) REFERENCES course(id),
                          CONSTRAINT fk_schedule_period_type FOREIGN KEY (period_type_id) REFERENCES period_type(id),
                          CONSTRAINT fk_schedule_classroom FOREIGN KEY (classroom_id) REFERENCES classroom(id)
);
CREATE INDEX idx_schedule_course ON schedule (course_id);
CREATE INDEX idx_schedule_day ON schedule (day_of_week);

CREATE TABLE attendance (
                            id bigint PRIMARY KEY,
                            schedule_id bigint NOT NULL,
                            student_id bigint NOT NULL,
                            date date NOT NULL,
                            status varchar(10) DEFAULT 'present',
                            recorded_at timestamp DEFAULT now(),
                            recorded_by bigint,
                            notes text,
                            CONSTRAINT fk_attendance_schedule FOREIGN KEY (schedule_id) REFERENCES schedule(id),
                            CONSTRAINT fk_attendance_student FOREIGN KEY (student_id) REFERENCES student(id),
                            CONSTRAINT fk_attendance_teacher FOREIGN KEY (recorded_by) REFERENCES teacher(id),
                            CONSTRAINT uq_attendance UNIQUE (schedule_id, student_id, date)
);
CREATE INDEX idx_attendance_student ON attendance (student_id);

CREATE TABLE lesson_topic (
                              id bigint PRIMARY KEY,
                              schedule_id bigint NOT NULL,
                              date date NOT NULL,
                              topic varchar(128) NOT NULL,
                              description text,
                              materials text,
                              homework_id bigint,
                              created_by bigint NOT NULL,
                              created_at timestamp DEFAULT now(),
                              CONSTRAINT fk_lesson_topic_schedule FOREIGN KEY (schedule_id) REFERENCES schedule(id),
                              CONSTRAINT fk_lesson_topic_homework FOREIGN KEY (homework_id) REFERENCES assignment(id),
                              CONSTRAINT fk_lesson_topic_teacher FOREIGN KEY (created_by) REFERENCES teacher(id)
);
CREATE INDEX idx_lesson_topic_schedule ON lesson_topic (schedule_id, date);