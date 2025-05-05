package ru.edu.platform.structure.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.platform.structure.domain.service.FacultyService;
import ru.edu.platform.structure.exceptions.FacultyNotFoundException;
import ru.edu.platform.structure.web.dto.FacultiesDto;
import ru.edu.platform.structure.web.dto.DepartmentsDto;
import ru.edu.platform.structure.web.mapper.DepartmentsDtoMapper;
import ru.edu.platform.structure.web.mapper.FacultyDtoMapper;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyResource {
  private final FacultyService facultyService;

  public FacultyResource(FacultyService facultyService) {
    this.facultyService = facultyService;
  }

  @GetMapping
  public ResponseEntity<FacultiesDto> getAll() {
    try {
      List<FacultiesDto.FacultyDto> faculties = facultyService.getAll().stream()
        .map(FacultyDtoMapper.INSTANCE::toDto)
        .toList();
      return ResponseEntity.ok(
        new FacultiesDto(faculties)
      );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }


  @GetMapping("{id}/department")
  public ResponseEntity<DepartmentsDto> getAllByFacultyId(
    @PathVariable Long id
  ) {
    try {
      List<DepartmentsDto.DepartmentDto> departments = facultyService.getAllByFacultyId(id).stream()
        .map(DepartmentsDtoMapper.INSTANCE::toDto)
        .toList();
      return ResponseEntity.ok(
        new DepartmentsDto(departments)
      );
    } catch (FacultyNotFoundException ex) {
      return ResponseEntity.notFound().build();
    } catch (Exception ex) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
