package ru.edu.platform.structure.data.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.edu.platform.structure.domain.container.Campus;
import ru.edu.platform.structure.domain.repository.CampusRepository;

import java.util.List;

@Repository
public class CampusRepositoryImpl implements CampusRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final RowMapper<Campus> mapper;

  private static final String EXISTS_BY_ID_QUERY = """
      SELECT EXISTS (
          SELECT 1 FROM campus WHERE id = :id
      )
    """;

  private static final String GET_ALL_CAMPUSES_QUERY = """
      SELECT * FROM campus ORDER BY campus_name
    """;

  public CampusRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate,
                              RowMapper<Campus> campusRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = campusRowMapper;
  }

  @Override
  public List<Campus> getAll() {
    return jdbcTemplate.query(
      GET_ALL_CAMPUSES_QUERY, mapper
    );
  }

  @Override
  public boolean exist(Long id) {
    if (id == null) {
      return false;
    }
    SqlParameterSource params = new MapSqlParameterSource("id", id);

    Boolean exists = jdbcTemplate.queryForObject(
      EXISTS_BY_ID_QUERY, params, Boolean.class
    );

    return Boolean.TRUE.equals(exists);
  }
}
