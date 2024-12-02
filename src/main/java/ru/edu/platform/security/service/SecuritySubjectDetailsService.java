package ru.edu.platform.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.edu.platform.security.domain.SecurityGrant;
import ru.edu.platform.security.domain.SecuritySubject;
import ru.edu.platform.security.domain.adapter.SecuritySubjectUserDetails;
import ru.edu.platform.security.repository.SecurityGrantRepository;
import ru.edu.platform.security.repository.SecuritySubjectRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SecuritySubjectDetailsService implements UserDetailsService {

    private final SecuritySubjectRepository securitySubjectRepository;
    private final SecurityGrantRepository securityGrantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecuritySubject> secSubjOpt = securitySubjectRepository.findOneByName(username);
        if (secSubjOpt.isEmpty()) {
            throw new UsernameNotFoundException("user not found: " + username);
        }
        SecuritySubject securitySubject = secSubjOpt.get();
        List<SecurityGrant> grants = securityGrantRepository.findAllBySubjectId(securitySubject.getId());
        return SecuritySubjectUserDetails.of(securitySubject, grants);
    }
}
