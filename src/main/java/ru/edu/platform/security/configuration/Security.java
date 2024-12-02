package ru.edu.platform.security.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.apache.logging.log4j.util.StackLocatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.edu.platform.configuration.PlatformProperties;
import ru.edu.platform.security.domain.SecurityGrant;
import ru.edu.platform.security.domain.SecuritySubject;
import ru.edu.platform.security.domain.adapter.GrantToAuthorityAdapter;
import ru.edu.platform.security.domain.adapter.SecuritySubjectUserDetails;
import ru.edu.platform.security.repository.SecurityGrantRepository;
import ru.edu.platform.security.repository.SecuritySubjectRepository;
import ru.edu.platform.security.service.CompositeUserDetailsService;
import ru.edu.platform.security.service.JwtTokenService;
import ru.edu.platform.security.service.JwtTokenServiceImpl;
import ru.edu.platform.security.service.SecuritySubjectDetailsService;
import ru.edu.platform.util.JwtAuthoritiesConverter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class Security {

    private final Logger logger = LoggerFactory.getLogger(StackLocatorUtil.getCallerClass(1));

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security,
                                            JwtDecoder decoder,
                                            JwtAuthoritiesConverter authoritiesConverter
    ) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> {
                            authorize.requestMatchers("/api/auth/**").permitAll()
                                    .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                                    .anyRequest().authenticated();
                        }
                ).oauth2ResourceServer(
                        oauth2 ->
                                oauth2.jwt(jwt -> {
                                    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
                                    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
                                    jwt.decoder(decoder);
                                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);
                                })
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return security.build();
    }

    @Bean
    PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    AuthenticationManager authenticationManager(List<AuthenticationProvider> providers) {
        ProviderManager providerManager = new ProviderManager(providers);
        return providerManager;
    }

    @Bean
    AuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
                                                     PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    UserDetailsService userDetailsService(
            PlatformProperties properties,
            PasswordEncoder passwordEncoder,
            SecuritySubjectRepository securitySubjectRepository,
            SecurityGrantRepository securityGrantRepository
    ) {
        SecuritySubjectDetailsService securitySubjectDetailsService = new SecuritySubjectDetailsService(securitySubjectRepository, securityGrantRepository);

        Optional<List<PlatformProperties.User>> stubUsers = Optional.ofNullable(properties)
                .map(PlatformProperties::getSecurity)
                .map(PlatformProperties.Security::getStubDetailsService)
                .map(PlatformProperties.StubDetailsService::getUsers);
        UserDetailsService userDetailsService;
        if (stubUsers.isPresent()) {
            List<PlatformProperties.User> sds = stubUsers.get();
            InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

            for (PlatformProperties.User user : sds) {
                List<SecurityGrant> grants = user.getAuthorities().stream().map(authority -> {
                    SecurityGrant securityGrant = new SecurityGrant();
                    securityGrant.setName(authority);
                    return securityGrant;
                }).collect(Collectors.toList());
                SecuritySubject securitySubject = new SecuritySubject();
                securitySubject.setUsername(user.getUsername());
                securitySubject.setPwd(passwordEncoder.encode(user.getPassword()));
                inMemoryUserDetailsManager.createUser(SecuritySubjectUserDetails.of(securitySubject, grants));
            }
            userDetailsService = new CompositeUserDetailsService(inMemoryUserDetailsManager, securitySubjectDetailsService);
        } else {
            userDetailsService = securitySubjectDetailsService;
        }
        logger.info("Construct UserDetailsService bean with implementation: {}", userDetailsService.getClass().getName());
        return userDetailsService;
    }

    @Bean
    JwtDecoder jwtDecoder(PlatformProperties properties) {
        PlatformProperties.RSA rsa = properties.getSecurity().getJwt().getRsa();
        RSAPublicKey publicKey = rsa.getPublicKey();
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder(PlatformProperties properties) {
        PlatformProperties.RSA rsa = properties.getSecurity().getJwt().getRsa();
        RSAPrivateKey privateKey = rsa.getPrivateKey();
        RSAPublicKey publicKey = rsa.getPublicKey();
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }


    @Bean
    JwtAuthoritiesConverter JwtAuthoritiesConverter(SecurityGrantRepository securityGrantRepository) {
        Function<String, GrantedAuthority> authorityMapFunction = new AuthorityMapFunction(securityGrantRepository);
        return new JwtAuthoritiesConverter(authorityMapFunction);
    }

    @Bean
    JwtTokenService jwtTokenService(JwtEncoder encoder) {
        return new JwtTokenServiceImpl(encoder);
    }


    private static class AuthorityMapFunction implements Function<String, GrantedAuthority> {
        private final SecurityGrantRepository securityGrantRepository;

        public AuthorityMapFunction(SecurityGrantRepository securityGrantRepository) {
            this.securityGrantRepository = securityGrantRepository;
        }

        @Override
        public GrantedAuthority apply(String name) {
            Optional<SecurityGrant> oneByName = securityGrantRepository.findOneByName(name);
            if (oneByName.isPresent()) {
                return GrantToAuthorityAdapter.of(oneByName.get());
            }
            throw new IllegalArgumentException("unable map %s to GrantedAuthority".formatted(name));
        }
    }

}
