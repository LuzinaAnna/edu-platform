package ru.edu.platform.security.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.edu.platform.security.domain.SecurityGrant;
import ru.edu.platform.security.repository.SecurityGrantRepository;
import ru.edu.platform.util.transaction.Isolation;
import ru.edu.platform.util.transaction.Propagation;
import ru.edu.platform.util.transaction.TransactionTemplateConfigurer;
import ru.edu.platform.util.transaction.TransactionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.edu.platform.util.transaction.Configurers.*;

public class SecurityGrantRepositoryImpl implements SecurityGrantRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager platformTransactionManager;
    private static final RowMapper<SecurityGrant> ROW_MAPPER = (rs, rowNum) -> {
        String name = rs.getString("name");
        Long id = rs.getLong("id");
        SecurityGrant sg = new SecurityGrant();
        sg.setId(id);
        sg.setName(name);
        return sg;
    };
    private static final String SQL_FIND_ALL_BY_SUBJECT_ID = "SELECT security_grant.id AS id, security_grant.name AS name from security_grant inner join subject_grant sg on security_grant.id = sg.grant_id where sg.subject_id = :id";
    private static final String SQL_FIND_ONE_BY_NAME = "SELECT security_grant.id AS id, security_grant.name AS name from security_grant where security_grant.name = :name";

    public SecurityGrantRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, PlatformTransactionManager platformTransactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public List<SecurityGrant> findAllBySubjectId(Long subjectId) {
        return TransactionUtils.doInTransaction(
                composeTransactionTemplate(
                        readonly(),
                        withIsolation(Isolation.READ_COMMITTED),
                        withPropagation(Propagation.REQUIRED),
                        withTransactionManager(platformTransactionManager)
                ),
                (u) -> {
                    List<SecurityGrant> securityGrant = jdbcTemplate.query(
                      SQL_FIND_ALL_BY_SUBJECT_ID,
                      Map.of("id", subjectId),
                      ROW_MAPPER
                    );
                    return securityGrant;
                }
        );
    }

    @Override
    public Optional<SecurityGrant> findOneByName(String name) {
        return TransactionUtils.doInTransaction(
                composeTransactionTemplate(
                        readonly(),
                        withIsolation(Isolation.READ_COMMITTED),
                        withPropagation(Propagation.REQUIRED),
                        withTransactionManager(platformTransactionManager)
                ),
                (u) -> {
                    SecurityGrant securityGrant = jdbcTemplate.queryForObject(
                      SQL_FIND_ONE_BY_NAME,
                      Map.of("name", name),
                      ROW_MAPPER
                    );
                    return Optional.ofNullable(securityGrant);
                }
        );
    }
}
