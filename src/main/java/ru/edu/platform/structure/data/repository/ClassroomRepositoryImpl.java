package ru.edu.platform.structure.data.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.edu.platform.structure.data.repository.mappers.ClassroomRowMapper;
import ru.edu.platform.structure.domain.container.Classroom;
import ru.edu.platform.structure.domain.repository.ClassesRepository;

import java.util.List;

@Repository
public class ClassroomRepositoryImpl implements ClassesRepository {
  private static final String GET_ALL_BY_CAMPUS_ID_QUERY = """
        SELECT cls.* FROM classroom cls
        WHERE cls.campus_id = :id
        ORDER BY cls.number
    """;

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final ClassroomRowMapper mapper;

  public ClassroomRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate,
                                 ClassroomRowMapper mapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = mapper;
  }

  @Override
  public List<Classroom> getAllByCampusId(Long campusId) {
    if (campusId == null) {
      throw new NullPointerException("campusId is null");
    }

    SqlParameterSource params = new MapSqlParameterSource("id", campusId);

    return jdbcTemplate.query(
      GET_ALL_BY_CAMPUS_ID_QUERY, params, mapper
    );
  }
}
