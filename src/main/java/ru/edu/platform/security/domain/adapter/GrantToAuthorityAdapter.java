package ru.edu.platform.security.domain.adapter;

import org.springframework.security.core.GrantedAuthority;
import ru.edu.platform.security.domain.SecurityGrant;

import java.util.Objects;

public class GrantToAuthorityAdapter implements GrantedAuthority {

    private final SecurityGrant grant;

    private GrantToAuthorityAdapter(SecurityGrant grant) {
        Objects.requireNonNull(grant.getName(), "security grant should be not null");
        this.grant = grant;
    }

    @Override
    public String getAuthority() {
        return grant.getName();
    }

    public static GrantToAuthorityAdapter of(SecurityGrant securityGrant) {
        return new GrantToAuthorityAdapter(securityGrant);
    }
}
