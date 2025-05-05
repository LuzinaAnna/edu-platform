package ru.edu.platform.structure.exceptions;

import lombok.Getter;

@Getter
public class CampusNotFoundByIdException extends RuntimeException {
  private final Long campusId;

  public CampusNotFoundByIdException(Long campusId) {
    this.campusId = campusId;
  }
}
