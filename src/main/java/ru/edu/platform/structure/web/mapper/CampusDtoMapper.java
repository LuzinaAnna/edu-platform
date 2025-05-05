package ru.edu.platform.structure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.edu.platform.structure.domain.container.Campus;
import ru.edu.platform.structure.web.dto.CampusesDto;

@Mapper
public interface CampusDtoMapper {
  CampusDtoMapper INSTANCE = Mappers.getMapper(CampusDtoMapper.class);


  @Mapping(source = "campusName", target = "name")
  CampusesDto.CampusEntryDto toDto(Campus campus);
}
