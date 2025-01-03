package ru.edu.platform.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {
    String issueAccessToken(UserDetails userDetails);

    String issueRefreshToken(UserDetails userDetails);
}
