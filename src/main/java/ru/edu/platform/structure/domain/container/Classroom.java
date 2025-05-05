package ru.edu.platform.structure.domain.container;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class Classroom {
  private Long id;
  private String number;
  private Long campusId;
  private Integer capacity;
  private RoomType roomType;
  private Timestamp createdAt;
}
