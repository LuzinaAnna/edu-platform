package ru.edu.platform.common.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "platform")
@Data
public class PlatformProperties {
    private Datasource datasource;
    private Security security;

    @Data
    public static class Datasource {
        private String jdbcUrl;
        private String username;
        private String password;
        private PoolSettings poolSettings;
    }

    @Data
    public static class PoolSettings {
        private String poolName;
        private int maximumPoolSize = 20;
        private int connectionTimeout = 180_000;
        private int idleTimeout = 180_000;
        private int leakDetectionThreshold = 20_000;
        private int maxLifetime = 180_000;
    }

    @Data
    public static class Security {
        private JWT jwt;
        private StubDetailsService stubDetailsService = null;
    }

    @Data
    public static class JWT {
        private RSA rsa;
    }

    @Data
    public static class RSA {
        private RSAPrivateKey privateKey;
        private RSAPublicKey publicKey;
    }

    /**
     * Test purpose only.
     * <pre>
     * stub-details-service:
     * users:
     * -
     * username: admin
     * password: adminka
     * authorities:
     * - ADMIN
     * </pre>
     */
    @Data
    public static class StubDetailsService {
        private List<User> users;
    }

    @Data
    public static class User {
        private String username;
        private String password;
        private List<String> authorities;
    }
}
