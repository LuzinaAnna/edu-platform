package ru.edu.platform.structure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.edu.platform.structure.domain.container.Classroom;
import ru.edu.platform.structure.web.dto.ClassroomsDto;

@Mapper
public interface ClassroomDtoMapper {
  ClassroomDtoMapper INSTANCE = Mappers.getMapper(ClassroomDtoMapper.class);

  ClassroomsDto.ClassroomDto toDto(Classroom classroom);
}