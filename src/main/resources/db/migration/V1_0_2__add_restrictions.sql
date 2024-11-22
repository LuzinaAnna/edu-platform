ALTER TABLE classroom DROP CONSTRAINT campus_classroom_fk;
ALTER TABLE classroom ADD CONSTRAINT campus_classroom_fk FOREIGN KEY (campus_id) REFERENCES campus(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE department DROP CONSTRAINT faculty_department_fk;
ALTER TABLE department ADD CONSTRAINT faculty_department_fk FOREIGN KEY (faculty_id) REFERENCES faculty(id) ON DELETE CASCADE ON UPDATE CASCADE;