package ru.edu.platform.structure.data.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.edu.platform.structure.domain.container.Classroom;
import ru.edu.platform.structure.domain.container.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassroomRowMapper implements RowMapper<Classroom> {
  @Override
  public Classroom mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Classroom.builder()
      .id(rs.getLong("id"))
      .number(rs.getString("number"))
      .campusId(rs.getLong("campus_id"))
      .roomType(
        RoomType.valueOf(rs.getString("room_type"))
      )
      .createdAt(rs.getTimestamp("created_at"))
      .build();
  }
}
