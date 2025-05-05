package ru.edu.platform.structure.data.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.edu.platform.structure.domain.container.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DepartmentRowMapper implements RowMapper<Department> {
  @Override
  public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
    return null;
  }
}
