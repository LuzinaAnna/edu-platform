package ru.edu.platform.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.util.StackLocatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DbConfiguration {
    Logger logger = LoggerFactory.getLogger(StackLocatorUtil.getCallerClass(1));

    @Bean(destroyMethod = "close")
    HikariDataSource dataSource(PlatformProperties properties) {
        PlatformProperties.Datasource datasourceConfiguration =
                properties.getDatasource();
        PlatformProperties.PoolSettings poolSettings = datasourceConfiguration.getPoolSettings();
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(datasourceConfiguration.getJdbcUrl());
        config.setUsername(datasourceConfiguration.getUsername());
        config.setPassword(datasourceConfiguration.getPassword());

        config.setMaximumPoolSize(poolSettings.getMaximumPoolSize());
        config.setConnectionTimeout(poolSettings.getConnectionTimeout());
        config.setIdleTimeout(poolSettings.getIdleTimeout());
        config.setLeakDetectionThreshold(poolSettings.getLeakDetectionThreshold());
        config.setMaxLifetime(poolSettings.getMaxLifetime());
        config.setPoolName(poolSettings.getPoolName());
        return new HikariDataSource(config);
    }


    @Bean
    NamedParameterJdbcTemplate namedJdbcTemplate    (DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
