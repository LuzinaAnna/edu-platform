package ru.edu.platform.security.repository.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.edu.platform.security.domain.SecuritySubject;
import ru.edu.platform.security.repository.SecuritySubjectRepository;
import ru.edu.platform.common.util.transaction.Isolation;
import ru.edu.platform.common.util.transaction.Propagation;
import ru.edu.platform.common.util.transaction.TransactionUtils;

import java.util.Map;
import java.util.Optional;

import static ru.edu.platform.common.util.transaction.Configurers.*;

public class SecuritySubjectRepositoryImpl implements SecuritySubjectRepository {

    private static final String SQL_FIND_ONE_BY_ID =
            """
                                    SELECT
                                    id,
                                    username,
                                    pwd,
                                    email,
                                    is_expired,
                                    is_locked,
                                    is_pwd_expired,
                                    is_enabled
                                    FROM security_subject WHERE id = :id
                    """;

    private static final String SQL_FIND_ONE_BY_NAME =
            """
                                    SELECT
                                    id,
                                    username,
                                    pwd,
                                    email,
                                    is_expired,
                                    is_locked,
                                    is_pwd_expired,
                                    is_enabled
                                    FROM security_subject WHERE username = :name
                    """;
    private static final RowMapper<SecuritySubject> ROW_MAPPER = (rs, rowNum) -> {
        SecuritySubject ss = new SecuritySubject();
        long id = rs.getLong("id");
        String username = rs.getString("username");
        String pwd = rs.getString("pwd");
        String email = rs.getString("email");
        boolean isExpired = rs.getBoolean("is_expired");
        boolean isLocked = rs.getBoolean("is_locked");
        boolean isPwdExpired = rs.getBoolean("is_pwd_expired");
        boolean isEnabled = rs.getBoolean("is_enabled");
        ss.setId(id);
        ss.setUsername(username);
        ss.setPwd(pwd);
        ss.setEmail(email);
        ss.setExpired(isExpired);
        ss.setLocked(isLocked);
        ss.setPwdExpired(isPwdExpired);
        ss.setEnabled(isEnabled);
        return ss;
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    public SecuritySubjectRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<SecuritySubject> findOneById(Long id) {
        return TransactionUtils.doInTransaction(
                composeTransactionTemplate(
                        readonly(),
                        withIsolation(Isolation.READ_COMMITTED),
                        withPropagation(Propagation.REQUIRED),
                        withTransactionManager(transactionManager)
                ),
                (u) -> {
                    SecuritySubject securitySubject = jdbcTemplate.queryForObject(
                      SQL_FIND_ONE_BY_ID,
                      Map.of("id", id),
                      ROW_MAPPER
                    );
                    return Optional.ofNullable(securitySubject);
                }
        );
    }

    @Override
    public Optional<SecuritySubject> findOneByName(String name) {
        return TransactionUtils.doInTransaction(
                composeTransactionTemplate(
                        readonly(),
                        withIsolation(Isolation.READ_COMMITTED),
                        withPropagation(Propagation.REQUIRED),
                        withTransactionManager(transactionManager)
                ),
                (u) -> {
                    SecuritySubject securitySubject = jdbcTemplate.queryForObject(
                      SQL_FIND_ONE_BY_NAME,
                      Map.of("name", name),
                      ROW_MAPPER
                    );
                    return Optional.ofNullable(securitySubject);
                }
        );
    }
}
