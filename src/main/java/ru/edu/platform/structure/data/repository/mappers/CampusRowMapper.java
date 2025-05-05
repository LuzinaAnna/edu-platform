package ru.edu.platform.structure.data.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.edu.platform.structure.domain.container.Campus;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CampusRowMapper implements RowMapper<Campus> {
  @Override
  public Campus mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Campus.builder()
      .id(rs.getLong("id"))
      .campusName(rs.getString("campus_name"))
      .address(rs.getString("address"))
      .createdAt(rs.getTimestamp("created_at"))
      .updatedAt(rs.getTimestamp("updated_at"))
      .build();
  }
}
