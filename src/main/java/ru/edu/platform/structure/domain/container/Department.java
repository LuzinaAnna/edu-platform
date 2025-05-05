package ru.edu.platform.structure.domain.container;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class Department {
  private Long id;
  private String departmentName;
  private Long facultyId;
  private Long headId;
  private Timestamp createdAt;
}
