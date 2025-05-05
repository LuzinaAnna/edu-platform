package ru.edu.platform.structure.domain.repository;

import ru.edu.platform.structure.domain.container.Faculty;

import java.util.List;

public interface FacultyRepository {
  List<Faculty> getAll();

  boolean exists(Long id);
}
