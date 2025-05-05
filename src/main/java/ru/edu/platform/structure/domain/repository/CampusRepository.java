package ru.edu.platform.structure.domain.repository;

import ru.edu.platform.structure.domain.container.Campus;

import java.util.List;

public interface CampusRepository {
  List<Campus> getAll();

  boolean exist(Long id);
}
