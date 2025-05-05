package ru.edu.platform.structure.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.platform.structure.domain.service.BuildingsService;
import ru.edu.platform.structure.exceptions.CampusNotFoundByIdException;
import ru.edu.platform.structure.web.dto.CampusesDto;
import ru.edu.platform.structure.web.dto.ClassroomsDto;
import ru.edu.platform.structure.web.mapper.CampusDtoMapper;
import ru.edu.platform.structure.web.mapper.ClassroomDtoMapper;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/campus")
public class CampusResource {
  private final BuildingsService buildingsService;

  public CampusResource(BuildingsService buildingsService) {
    this.buildingsService = buildingsService;
  }

  @GetMapping
  public ResponseEntity<CampusesDto> getAll() {
    try {
      List<CampusesDto.CampusEntryDto> campusesDto = buildingsService.getAllCampuses().stream()
        .map(CampusDtoMapper.INSTANCE::toDto)
        .sorted(Comparator.comparing(CampusesDto.CampusEntryDto::getName))
        .toList();
      return ResponseEntity.ok()
        .body(new CampusesDto(campusesDto));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("{id}/classroom")
  public ResponseEntity<ClassroomsDto> getAllByCampusId(@PathVariable Long id) {
    try {
      List<ClassroomsDto.ClassroomDto> classrooms =  buildingsService.getClassesByCampusId(id).stream()
        .map(ClassroomDtoMapper.INSTANCE::toDto)
        .sorted(Comparator.comparing(ClassroomsDto.ClassroomDto::getNumber))
        .toList();
      return ResponseEntity.ok()
        .body(new ClassroomsDto(classrooms));
    } catch (CampusNotFoundByIdException ex) {
      return ResponseEntity.notFound().build();
    } catch (RuntimeException ex) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
