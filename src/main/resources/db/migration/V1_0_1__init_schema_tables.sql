-- Core academic structures
CREATE TABLE academic_year (
                               id bigint PRIMARY KEY,
                               year_name varchar(32) NOT NULL,
                               start_date date NOT NULL,
                               end_date date NOT NULL,
                               is_current boolean DEFAULT false,
                               CONSTRAINT uq_academic_year_name UNIQUE (year_name)
);

CREATE TABLE semester (
                          id bigint PRIMARY KEY,
                          academic_year_id bigint NOT NULL,
                          semester_name varchar(64) NOT NULL,
                          "from" date NOT NULL,
                          "to" date NOT NULL,
                          is_current boolean DEFAULT false,
                          CONSTRAINT fk_semester_academic_year FOREIGN KEY (academic_year_id) REFERENCES academic_year(id),
                          CONSTRAINT uq_semester_name UNIQUE (academic_year_id, semester_name)
);

CREATE TABLE education_level (
                                 id bigint PRIMARY KEY,
                                 level_name varchar(64) NOT NULL,
                                 CONSTRAINT uq_education_level UNIQUE (level_name)
);

CREATE TABLE education_form (
                                id bigint PRIMARY KEY,
                                form_name varchar(64) NOT NULL,
                                CONSTRAINT uq_education_form UNIQUE (form_name)
);

CREATE TABLE campus (
                        id bigint PRIMARY KEY,
                        campus_name varchar(64) NOT NULL,
                        address varchar(128) NOT NULL,
                        created_at timestamp DEFAULT now(),
                        updated_at timestamp DEFAULT now(),
                        CONSTRAINT uq_campus_name UNIQUE (campus_name)
);

CREATE TABLE classroom (
                           id bigint PRIMARY KEY,
                           number varchar(16) NOT NULL,
                           campus_id bigint NOT NULL,
                           capacity integer,
                           room_type varchar(10) DEFAULT 'lecture',
                           created_at timestamp DEFAULT now(),
                           CONSTRAINT fk_classroom_campus FOREIGN KEY (campus_id) REFERENCES campus(id),
                           CONSTRAINT uq_classroom_number UNIQUE (campus_id, number)
);

CREATE TABLE period_type (
                             id bigint PRIMARY KEY,
                             period_name varchar(64) NOT NULL,
                             start_time time NOT NULL,
                             end_time time NOT NULL,
                             CONSTRAINT uq_period_name UNIQUE (period_name)
);