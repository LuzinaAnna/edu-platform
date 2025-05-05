package ru.edu.platform.structure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ClassroomsDto {
  private final List<ClassroomDto> classrooms;

  @Data
  public static class ClassroomDto {
    private Long id;
    private String number;
    private Integer capacity;
    private String roomType;
  }
}
