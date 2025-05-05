package ru.edu.platform.structure.domain.container;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class Campus {
  private Long id;
  private String campusName;
  private String address;
  private Timestamp createdAt;
  private Timestamp updatedAt;
}
