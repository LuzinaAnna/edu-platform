package ru.edu.platform.structure.domain.service.impl;

import org.springframework.stereotype.Service;
import ru.edu.platform.structure.domain.container.Department;
import ru.edu.platform.structure.domain.container.Faculty;
import ru.edu.platform.structure.domain.repository.DepartmentRepository;
import ru.edu.platform.structure.domain.repository.FacultyRepository;
import ru.edu.platform.structure.domain.service.FacultyService;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
  private final FacultyRepository facultyRepository;
  private final DepartmentRepository departmentRepository;

  public FacultyServiceImpl(FacultyRepository facultyRepository,
                            DepartmentRepository departmentRepository) {
    this.facultyRepository = facultyRepository;
    this.departmentRepository = departmentRepository;
  }

  @Override
  public List<Faculty> getAll() {
    return List.of();
  }

  @Override
  public List<Department> getAllByFacultyId(Long facultyId) {
    return List.of();
  }
}
