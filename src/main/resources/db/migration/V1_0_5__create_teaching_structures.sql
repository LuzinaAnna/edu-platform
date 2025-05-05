

CREATE TABLE enrollment (
                            id bigint PRIMARY KEY,
                            course_id bigint NOT NULL,
                            student_id bigint NOT NULL,
                            enrollment_date timestamp DEFAULT now(),
                            status varchar(10) DEFAULT 'active',
                            final_mark_id bigint,
                            CONSTRAINT fk_enrollment_course FOREIGN KEY (course_id) REFERENCES course(id),
                            CONSTRAINT fk_enrollment_student FOREIGN KEY (student_id) REFERENCES student(id),
                            CONSTRAINT fk_enrollment_mark FOREIGN KEY (final_mark_id) REFERENCES mark(id),  -- Now mark exists
                            CONSTRAINT uq_enrollment UNIQUE (course_id, student_id)
);
CREATE INDEX idx_enrollment_student ON enrollment (student_id);