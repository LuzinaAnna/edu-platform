package ru.edu.platform.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.edu.platform.repository.SequenceRepository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PostgresqlSequenceRepository implements SequenceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    @Override
    public List<Long> take(String sequenceName, Long n) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute(a -> jdbcTemplate.queryForList("SELECT nextval(:sequenceName)", Long.class, sequenceName));
    }
}
