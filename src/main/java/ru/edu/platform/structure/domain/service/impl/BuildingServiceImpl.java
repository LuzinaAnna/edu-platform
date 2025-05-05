package ru.edu.platform.structure.domain.service.impl;

import org.springframework.stereotype.Service;
import ru.edu.platform.structure.exceptions.CampusNotFoundByIdException;
import ru.edu.platform.structure.domain.service.BuildingsService;
import ru.edu.platform.structure.domain.repository.CampusRepository;
import ru.edu.platform.structure.domain.repository.ClassesRepository;
import ru.edu.platform.structure.domain.container.Campus;
import ru.edu.platform.structure.domain.container.Classroom;

import java.util.Comparator;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingsService {
  private final CampusRepository campusRepository;
  private final ClassesRepository classesRepository;

  public BuildingServiceImpl(CampusRepository campusRepository, ClassesRepository classesRepository) {
    this.campusRepository = campusRepository;
    this.classesRepository = classesRepository;
  }

  @Override
  public List<Campus> getAllCampuses() {
    return campusRepository.getAll();
  }

  @Override
  public List<Classroom> getClassesByCampusId(Long id) {
    if (!campusRepository.exist(id)) {
      throw new CampusNotFoundByIdException(id);
    }
    List<Classroom> classes = classesRepository.getAllByCampusId(id);
    return classes.stream()
      .sorted(Comparator.comparing(Classroom::getNumber))
      .toList();
  }
}
