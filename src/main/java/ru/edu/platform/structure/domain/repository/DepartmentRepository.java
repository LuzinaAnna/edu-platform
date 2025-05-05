package ru.edu.platform.structure.domain.repository;

import ru.edu.platform.structure.domain.container.Department;

import java.util.List;

public interface DepartmentRepository {
  List<Department> getAllByFacultyId(Long facultyId);
}
