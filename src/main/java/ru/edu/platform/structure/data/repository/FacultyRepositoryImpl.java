package ru.edu.platform.structure.data.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.edu.platform.structure.data.repository.mappers.FacultyRowMapper;
import ru.edu.platform.structure.domain.container.Faculty;
import ru.edu.platform.structure.domain.repository.FacultyRepository;

import java.util.List;

@Repository
public class FacultyRepositoryImpl implements FacultyRepository {
  private static final String GET_ALL_FACULTY_QUERY = """
        SELECT * FROM faculty;
    """;

  private static final String EXIST_BY_ID_QUERY = """
        SELECT EXISTS (
          SELECT 1 FROM faculty WHERE id = :id
        )
    """;

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final RowMapper<Faculty> mapper;

  public FacultyRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate,
                               FacultyRowMapper mapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = mapper;
  }

  @Override
  public List<Faculty> getAll() {
    return jdbcTemplate.query(GET_ALL_FACULTY_QUERY, mapper);
  }

  @Override
  public boolean exists(Long id) {
    if (id == null) {
      return false;
    }
    SqlParameterSource params = new MapSqlParameterSource("id", id);

    Boolean exists = jdbcTemplate.queryForObject(
      EXIST_BY_ID_QUERY, params, Boolean.class
    );

    return Boolean.TRUE.equals(exists);
  }
}
