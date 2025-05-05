-- Academic organization structure
CREATE TABLE faculty (
                         id bigint PRIMARY KEY,
                         faculty_name varchar(64) NOT NULL,
                         dean_id bigint,
                         created_at timestamp DEFAULT now(),
                         CONSTRAINT uq_faculty_name UNIQUE (faculty_name)
);

CREATE TABLE department (
                            id bigint PRIMARY KEY,
                            department_name varchar(64) NOT NULL,
                            faculty_id bigint NOT NULL,
                            head_id bigint,
                            created_at timestamp DEFAULT now(),
                            CONSTRAINT fk_department_faculty FOREIGN KEY (faculty_id) REFERENCES faculty(id),
                            CONSTRAINT uq_department_name UNIQUE (faculty_id, department_name)
);

CREATE TABLE discipline (
                            id bigint PRIMARY KEY,
                            discipline_name varchar(64) NOT NULL,
                            discipline_code varchar(16) NOT NULL,
                            credits numeric(3,1) NOT NULL,
                            department_id bigint NOT NULL,
                            description text,
                            created_at timestamp DEFAULT now(),
                            CONSTRAINT fk_discipline_department FOREIGN KEY (department_id) REFERENCES department(id),
                            CONSTRAINT uq_discipline_code UNIQUE (discipline_code)
);
CREATE INDEX idx_discipline_name ON discipline (discipline_name);