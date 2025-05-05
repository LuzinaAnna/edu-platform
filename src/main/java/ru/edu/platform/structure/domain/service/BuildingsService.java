package ru.edu.platform.structure.domain.service;

import ru.edu.platform.structure.domain.container.Campus;
import ru.edu.platform.structure.domain.container.Classroom;

import java.util.List;

public interface BuildingsService {
  List<Campus> getAllCampuses();

  List<Classroom> getClassesByCampusId(Long id);
}
