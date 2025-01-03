package ru.edu.platform.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String DEFAULT_SCOPE_CLAIM = "scp";
    private static final String DEFAULT_AUTHORITIES_DELIMITER = " ";
    private final String scopeClaim;
    private final String authoritiesDelimiter;
    private final Function<String, GrantedAuthority> authorityMapper;

    public JwtAuthoritiesConverter(Function<String, GrantedAuthority> authorityMapper, String scopeClaim, String authoritiesDelimiter) {
        this.authorityMapper = authorityMapper;
        this.scopeClaim = scopeClaim;
        this.authoritiesDelimiter = authoritiesDelimiter;
    }

    public JwtAuthoritiesConverter(Function<String, GrantedAuthority> authorityMapper, String scopeClaim) {
        this(authorityMapper, scopeClaim, DEFAULT_AUTHORITIES_DELIMITER);
    }

    public JwtAuthoritiesConverter(Function<String, GrantedAuthority> authorityMapper) {
        this(authorityMapper, DEFAULT_SCOPE_CLAIM);
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String authority : getAuthorities(source)) {
            GrantedAuthority mappedAuthority = mapAuthority(authority);
            authorities.add(mappedAuthority);
        }
        return authorities;
    }

    protected GrantedAuthority mapAuthority(String authority) {
        return authorityMapper.apply(authority);
    }


    protected Collection<String> splitAuthoritiesString(String authorities) {
        return Arrays.asList(authorities.split(getDelimiter()));
    }

    protected Collection<String> mapAuthoritiesToStringCollection(Collection<?> authorities) {
        if (authorities.isEmpty()) {
            return List.of();
        }
        List<?> authoritiesLst = new ArrayList<>(authorities);
        Object au = authoritiesLst.get(0);
        if (au instanceof String) {
            return (List<String>) authoritiesLst;
        }
        return authoritiesLst.stream().map(Objects::toString).collect(Collectors.toList());
    }

    protected String getDelimiter() {
        return authoritiesDelimiter;
    }


    private Collection<String> getAuthorities(Jwt source) {
        Object authorities = source.getClaim(scopeClaim);
        if (authorities instanceof String) {
            if (StringUtils.isEmpty((String) authorities)) {
                return List.of();
            }
            return splitAuthoritiesString((String) authorities);
        }
        if (authorities instanceof Collection<?> authoritiesCollection) {
            if (authoritiesCollection.isEmpty()) {
                return List.of();
            }
            return mapAuthoritiesToStringCollection(authoritiesCollection);
        }
        return List.of();
    }
}
