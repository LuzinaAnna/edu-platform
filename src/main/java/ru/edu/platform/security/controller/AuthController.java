package ru.edu.platform.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.platform.security.controller.dto.CredentialsDto;
import ru.edu.platform.security.controller.dto.LoginDto;
import ru.edu.platform.security.service.JwtTokenService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtTokenService tokenService;
    private final AuthenticationManager providerManager;
    private final UserDetailsService userDetailsService;

    public AuthController(JwtTokenService tokenService,
                          AuthenticationManager providerManager,
                          UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.providerManager = providerManager;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginDto login(@RequestBody CredentialsDto credentialsDto) {
        // FIXME: tokens should be issued by another service.
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(credentialsDto.getUsername(), credentialsDto.getPassword());
        Authentication test = providerManager.authenticate(authRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(credentialsDto.getUsername());
        String accessToken = tokenService.issueAccessToken(userDetails);
        String refreshToken = tokenService.issueRefreshToken(userDetails);
        return LoginDto.builder().
                accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
