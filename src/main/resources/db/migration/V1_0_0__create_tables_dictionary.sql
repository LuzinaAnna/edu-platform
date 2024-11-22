CREATE TABLE campus(
    id INT PRIMARY KEY,
    campus_name VARCHAR(32),
    address VARCHAR(64) NOT NULL
);
CREATE TABLE classroom(
    id INT PRIMARY KEY,
    number INT NOT NULL,
    campus_id INT NOT NULL,
    CONSTRAINT campus_classroom_fk FOREIGN KEY (campus_id) REFERENCES campus(id) ON DELETE CASCADE
);
CREATE TABLE period_type(
    id INT PRIMARY KEY,
    period_name VARCHAR(64) NOT NULL
);
CREATE TABLE mark(
    id INT PRIMARY KEY,
    type_mark VARCHAR(64) NOT NULL
);
CREATE TABLE type_task(
    id INT PRIMARY KEY,
    task_name VARCHAR(64) NOT NULL
);
CREATE TABLE education_period(
    id INT PRIMARY KEY,
    "from" DATE NOT NULL,
    "to" DATE NOT NULL,
    period_name VARCHAR(64) NOT NULL
);
CREATE TABLE teaching_type(
     id INT PRIMARY KEY,
     teaching_name VARCHAR(64) NOT NULL
);
CREATE TABLE job_title(
     id INT PRIMARY KEY,
     job_name VARCHAR(64) NOT NULL
);
CREATE TABLE faculty(
    id INT PRIMARY KEY,
    faculty_name VARCHAR(64) NOT NULL
);
CREATE TABLE education_level(
    id INT PRIMARY KEY,
    level_name VARCHAR(64) NOT NULL
);
CREATE TABLE education_form(
    id INT PRIMARY KEY,
    form_name VARCHAR(64) NOT NULL
);
CREATE TABLE department(
    id INT PRIMARY KEY,
    department_name VARCHAR(64) NOT NULL,
    faculty_id INT NOT NULL,
    CONSTRAINT faculty_department_fk FOREIGN KEY (faculty_id) REFERENCES faculty(id) ON DELETE CASCADE
);
CREATE TABLE discipline(
    id INT PRIMARY KEY,
    discipline_name VARCHAR(64) NOT NULL
);