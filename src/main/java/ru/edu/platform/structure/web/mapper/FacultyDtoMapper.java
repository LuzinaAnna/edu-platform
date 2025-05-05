package ru.edu.platform.structure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.edu.platform.structure.domain.container.Faculty;
import ru.edu.platform.structure.web.dto.FacultiesDto;

@Mapper
public interface FacultyDtoMapper {
  FacultyDtoMapper INSTANCE = Mappers.getMapper(FacultyDtoMapper.class);

  @Mapping(source = "facultyName", target = "name")
  FacultiesDto.FacultyDto toDto(Faculty faculty);
}