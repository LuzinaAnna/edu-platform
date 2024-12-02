package ru.edu.platform.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.edu.platform.util.CompositeRuntimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CompositeUserDetailsService implements UserDetailsService {

    List<? extends UserDetailsService> services;

    public CompositeUserDetailsService(UserDetailsService... services) {
        this(Arrays.asList(services));
    }

    public CompositeUserDetailsService(Collection<? extends UserDetailsService> services) {
        this.services = new ArrayList<>(services);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        List<Exception> notFoundExceptions = new ArrayList<>(services.size());
        for (UserDetailsService service : services) {
            try {
                userDetails = service.loadUserByUsername(username);
                return userDetails;
            } catch (UsernameNotFoundException e) {
                notFoundExceptions.add(e);
            }
        }
        throw new UsernameNotFoundException("couldn't load user by username", new CompositeRuntimeException(notFoundExceptions));
    }
}
