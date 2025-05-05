package ru.edu.platform.structure.data.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.edu.platform.structure.data.repository.mappers.DepartmentRowMapper;
import ru.edu.platform.structure.domain.container.Department;
import ru.edu.platform.structure.domain.repository.DepartmentRepository;

import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {
  private static final String GET_ALL_BY_FACULTY_ID_QUERY = """
        SELECT d.* FROM department d
        WHERE d.faculty_id = :id
        ORDER BY d.department_name
    """;

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final RowMapper<Department> mapper;

  public DepartmentRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate,
                                  DepartmentRowMapper mapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = mapper;
  }

  @Override
  public List<Department> getAllByFacultyId(Long facultyId) {
    if (facultyId == null) {
      throw new NullPointerException("facultyId is null");
    }

    SqlParameterSource params = new MapSqlParameterSource("id", facultyId);

    return jdbcTemplate.query(
      GET_ALL_BY_FACULTY_ID_QUERY, params, mapper
    );
  }
}
