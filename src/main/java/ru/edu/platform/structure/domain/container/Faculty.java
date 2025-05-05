package ru.edu.platform.structure.domain.container;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class Faculty {
  private Long id;
  private String facultyName;
  private Long deanId;
  private Timestamp createdAt;
}
