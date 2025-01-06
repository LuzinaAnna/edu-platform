package ru.edu.platform.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.PlatformTransactionManager;
import ru.edu.platform.security.repository.SecurityGrantRepository;
import ru.edu.platform.security.repository.SecuritySubjectRepository;
import ru.edu.platform.security.repository.impl.CachedSecurityGrantRepository;
import ru.edu.platform.security.repository.impl.SecurityGrantRepositoryImpl;
import ru.edu.platform.security.repository.impl.SecuritySubjectRepositoryImpl;

@Configuration
public class Domain {
    @Bean
    SecurityGrantRepository securityGrantRepository(NamedParameterJdbcTemplate jdbcTemplate, PlatformTransactionManager platformTransactionManager) {
        return new CachedSecurityGrantRepository(new SecurityGrantRepositoryImpl(jdbcTemplate, platformTransactionManager));
    }

    @Bean
    SecuritySubjectRepository securitySubjectRepository(NamedParameterJdbcTemplate jdbcTemplate, PlatformTransactionManager platformTransactionManager) {
        return new SecuritySubjectRepositoryImpl(jdbcTemplate, platformTransactionManager);
    }
}
