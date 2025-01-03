package ru.edu.platform.security.domain.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.edu.platform.security.domain.SecurityGrant;
import ru.edu.platform.security.domain.SecuritySubject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecuritySubjectUserDetails implements UserDetails {
    private final SecuritySubject securitySubject;
    private final Collection<? extends GrantedAuthority> authorities;

    private SecuritySubjectUserDetails(SecuritySubject securitySubject, Collection<? extends GrantedAuthority> authorities) {
        this.securitySubject = securitySubject;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return securitySubject.getPwd();
    }

    @Override
    public String getUsername() {
        return securitySubject.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !securitySubject.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !securitySubject.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !securitySubject.isExpired();
    }

    @Override
    public boolean isEnabled() {
        return securitySubject.isEnabled();
    }

    public static SecuritySubjectUserDetails of(SecuritySubject subject, Collection<SecurityGrant> grants) {
        List<GrantToAuthorityAdapter> mapped = new ArrayList<>(grants.size());
        for (SecurityGrant grant : grants) {
            mapped.add(GrantToAuthorityAdapter.of(grant));
        }
        return new SecuritySubjectUserDetails(subject, mapped);
    }
}
