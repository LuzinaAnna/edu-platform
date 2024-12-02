package ru.edu.platform.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtEncoder encoder;
    private static final String DEFAULT_SCOPE_CLAIM = "scp";
    private static final String DEFAULT_ISSUER_NAME = "application@" + JwtTokenServiceImpl.class.getName();
    private static final TemporalAmount DEFAULT_ACCESS_TOKEN_EXPIRATION_TIMEOUT = Duration.ofMinutes(30);
    private static final TemporalAmount DEFAULT_REFRESH_TOKEN_EXPIRATION_TIMEOUT = Duration.ofHours(1);
    private String issuerName = DEFAULT_ISSUER_NAME;
    private String scopeClaim = DEFAULT_SCOPE_CLAIM;
    private TemporalAmount accessTokenExpirationTimeout = DEFAULT_ACCESS_TOKEN_EXPIRATION_TIMEOUT;
    private TemporalAmount refreshTokenExpirationTimeout = DEFAULT_REFRESH_TOKEN_EXPIRATION_TIMEOUT;


    public JwtTokenServiceImpl(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String issueAccessToken(UserDetails userDetails) {
        return issueInternal(userDetails, accessTokenExpirationTimeout);
    }

    @Override
    public String issueRefreshToken(UserDetails userDetails) {
        return issueInternal(userDetails, refreshTokenExpirationTimeout);
    }

    private String issueInternal(UserDetails userDetails, TemporalAmount expirationTimeout) {
        String scopes = getScopes(userDetails);
        Instant now = Instant.now();
        Instant expirationDeadline = now.plus(expirationTimeout);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .claim(this.scopeClaim, scopes)
                .issuedAt(now)
                .expiresAt(expirationDeadline)
                .issuer(issuerName)
                .build();
        return this.encoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    private static String getScopes(UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        StringBuilder result = new StringBuilder();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        int c = 0;
        while (iterator.hasNext()) {
            GrantedAuthority value = iterator.next();
            result.append(value.getAuthority());
            if (c != authorities.size() - 1) {
                result.append(" ");
            }
            ++c;
        }
        return result.toString();
    }

    public void setAccessTokenExpirationTimeout(TemporalAmount accessTokenExpirationTimeout) {
        Objects.requireNonNull(accessTokenExpirationTimeout);
        this.accessTokenExpirationTimeout = accessTokenExpirationTimeout;
    }

    public void setRefreshTokenExpirationTimeout(TemporalAmount refreshTokenExpirationTimeout) {
        Objects.requireNonNull(refreshTokenExpirationTimeout);
        this.refreshTokenExpirationTimeout = refreshTokenExpirationTimeout;
    }

    public void setIssuerName(String issuerName) {
        Objects.requireNonNull(issuerName);
        this.issuerName = issuerName;
    }

    public void setScopeClaim(String scopeClaim) {
        Objects.requireNonNull(scopeClaim);
        this.scopeClaim = scopeClaim;
    }
}
