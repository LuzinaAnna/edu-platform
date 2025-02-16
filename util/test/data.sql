-- Insert test data for campus
INSERT INTO campus (id, campus_name, address)
VALUES (1, 'Main Campus', '123 University St'),
       (2, 'East Campus', '456 College Ave');

-- Insert test data for classroom
INSERT INTO classroom (id, number, campus_id)
VALUES (1, 101, 1),
       (2, 202, 2);

-- Insert test data for period_type
INSERT INTO period_type (id, period_name)
VALUES (1, 'Every week'),
       (2, 'Every even week'),
       (3, 'Every odd week');

-- Insert test data for mark
INSERT INTO mark (id, type_mark)
VALUES (1, 'A'),
       (2, 'B'),
       (3, 'C');

-- Insert test data for education_period
INSERT INTO education_period (id, "from", "to", period_name)
VALUES (1, '2024-09-01', '2024-12-31', 'First semester 2024-2025'),
       (2, '2025-01-27', '2025-08-31', 'Second semester 2024-2025');

-- Insert test data for teaching_type
INSERT INTO teaching_type (id, teaching_name)
VALUES (1, 'Lecture'),
       (2, 'Practice');

-- Insert test data for job_title
INSERT INTO job_title (id, job_name)
VALUES (1, 'Professor'),
       (2, 'Docent');

-- Insert test data for faculty
INSERT INTO faculty (id, faculty_name)
VALUES (1, 'Electrotechnical');

-- Insert test data for education_level
INSERT INTO education_level (id, level_name)
VALUES (1, 'Bachelor s degree'),
       (2, 'Speciality');

-- Insert test data for education_form
INSERT INTO education_form (id, form_name)
VALUES (1, 'In person'),
       (2, 'In absentia');

-- Insert test data for department
INSERT INTO department (id, department_name, faculty_id)
VALUES (1, 'ITSA', 1);

-- Insert test data for discipline
INSERT INTO discipline (id, discipline_name)
VALUES (1, 'Data Base'),
       (2, 'OOP');

-- Insert test data for certification_form
INSERT INTO certification_form (id, form_name)
VALUES (1, 'Exam'),
       (2, 'Test');

-- Insert test data for teacher
INSERT INTO teacher (id, last_name, name, patronymic, job_title_id, department_id)
VALUES (1, 'Petrenko', 'Alexandr', 'Anatolyevich', 2, 1),
       (2, 'Polyakova', 'Olga', 'Andreevna', 1, 1);

-- Insert test data for group
INSERT INTO "group" (id, group_name, education_form_id, education_level_id, department_id, curator_id)
VALUES (1, 'CS101', 1, 1, 1, 1),
       (2, 'MATH202', 2, 2, 1, 2);

-- Insert test data for student
INSERT INTO student (id, last_name, name, patronymic, group_id, birthday)
VALUES (1, 'Luzina', 'Anna', 'Andreevna', 1, '2000-05-15'),
       (2, 'Semenov', 'Andrey', 'Olegovich', 2, '1999-08-22');


-- Insert test data for subject_taught
INSERT INTO subject_taught (id, education_period_id, teacher_id, discipline_id, group_id, teaching_type_id,
                            certification_form_id)
VALUES (1, 1, 1, 1, 1, 1, 1),
       (2, 2, 2, 2, 2, 2, 2);


-- Insert test data for schedule
INSERT INTO schedule (id, start_of_counting, subject_taught_id, period_type_id, classroom_id)
VALUES (1, '13:20', 1, 1, 1),
       (2, '11:30', 2, 2, 2),
       (3, '15:00', 1, 1, 1),
       (4, '13:20', 2, 2, 2),
       (5, '15:00', 1, 1, 1);

-- Create credentials for semenov.ao
INSERT INTO security_subject(id, username, pwd, email)
values (nextval('security_subject_id_seq'),
        'semenov.ao',
        '$2a$12$N6K9TlNxhW.NNPjrjowi5e6UmupL.lv0u3u7mJ41xEoyKSpi2RI.K', -- bcrypted value 'Root1234?'
        'semenov.andrey.2003@gmail.com');

INSERT INTO subject_grant(subject_id, grant_id)
VALUES ((select id from security_subject where username = 'semenov.ao'),
        (select id from security_grant where name = 'STUDENT'));

INSERT INTO subject_to_student(student_id, subject_id)
values (2, (select id from security_subject where username = 'semenov.ao'));

