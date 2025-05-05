package ru.edu.platform.structure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
public class DepartmentsDto {
  private final List<DepartmentDto> departments;

  @Data
  public static class DepartmentDto {
    private Long id;
    private String name;
  }
}
