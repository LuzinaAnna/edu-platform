package ru.edu.platform.structure.domain.service;

import ru.edu.platform.structure.domain.container.Department;
import ru.edu.platform.structure.domain.container.Faculty;

import java.util.List;

public interface FacultyService {
  List<Faculty> getAll();

  List<Department> getAllByFacultyId(Long facultyId);
}
