package ru.edu.platform.structure.web.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public final class CampusesDto {
  private final List<CampusEntryDto> campuses;

  public CampusesDto(List<CampusEntryDto> campuses) {
    this.campuses = campuses;
  }

  @Data
  public static class CampusEntryDto {
    private Long id;
    private String address;
    private String name;
  }
}