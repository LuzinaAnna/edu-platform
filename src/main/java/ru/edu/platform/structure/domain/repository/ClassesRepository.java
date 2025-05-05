package ru.edu.platform.structure.domain.repository;

import ru.edu.platform.structure.domain.container.Classroom;

import java.util.List;


public interface ClassesRepository {
  List<Classroom> getAllByCampusId(Long campusId);
}
