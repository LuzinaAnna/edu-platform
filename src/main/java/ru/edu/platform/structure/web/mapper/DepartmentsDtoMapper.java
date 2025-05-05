package ru.edu.platform.structure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.edu.platform.structure.domain.container.Department;
import ru.edu.platform.structure.web.dto.DepartmentsDto;

@Mapper
public interface DepartmentsDtoMapper {
  DepartmentsDtoMapper INSTANCE = Mappers.getMapper(DepartmentsDtoMapper.class);

  @Mapping(source = "departmentName", target = "name")
  DepartmentsDto.DepartmentDto toDto(Department department);
}
