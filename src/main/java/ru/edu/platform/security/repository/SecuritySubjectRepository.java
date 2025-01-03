package ru.edu.platform.security.repository;

import ru.edu.platform.security.domain.SecuritySubject;

import java.util.Optional;

public interface SecuritySubjectRepository {
    Optional<SecuritySubject> findOneById(Long id);
    Optional<SecuritySubject> findOneByName(String name);
}
