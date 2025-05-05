package ru.edu.platform.structure.exceptions;

import lombok.Getter;

@Getter
public class FacultyNotFoundException extends RuntimeException {
  private final Long facultyId;

  public FacultyNotFoundException(Long facultyId) {
    this.facultyId = facultyId;
  }
}
