package ru.edu.platform.security.repository;

import ru.edu.platform.security.domain.SecurityGrant;

import java.util.List;
import java.util.Optional;

public interface SecurityGrantRepository {
    List<SecurityGrant> findAllBySubjectId(Long subjectId);
    Optional<SecurityGrant> findOneByName(String name);
}
