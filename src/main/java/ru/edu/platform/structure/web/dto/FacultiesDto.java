package ru.edu.platform.structure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
public class FacultiesDto {
  private final List<FacultyDto> faculties;

  @Data
  public static class FacultyDto {
    private Long id;
    private String name;
  }
}
