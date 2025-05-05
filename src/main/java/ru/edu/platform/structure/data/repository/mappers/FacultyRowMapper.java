package ru.edu.platform.structure.data.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.edu.platform.structure.domain.container.Faculty;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FacultyRowMapper implements RowMapper<Faculty> {
  @Override
  public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
    return null;
  }
}
