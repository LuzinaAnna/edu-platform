package ru.edu.platform.sequence.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.edu.platform.sequence.repository.SequenceRepository;

import java.util.List;

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
