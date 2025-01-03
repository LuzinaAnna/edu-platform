package ru.edu.platform.security.repository.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import ru.edu.platform.security.domain.SecurityGrant;
import ru.edu.platform.security.repository.SecurityGrantRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class CachedSecurityGrantRepository implements SecurityGrantRepository {

    CacheLoader<String, Optional<SecurityGrant>> byNameCacheLoader;
    CacheLoader<Long, List<SecurityGrant>> bySecSubjectCacheLoader;


    private final SecurityGrantRepository securityGrantRepository;
    private Cache<String, Optional<SecurityGrant>> byNameCache;
    private Cache<Long, List<SecurityGrant>> bySecSubjectCache;

    public CachedSecurityGrantRepository(SecurityGrantRepository securityGrantRepository) {
        this.securityGrantRepository = securityGrantRepository;
        init(securityGrantRepository);
    }

    private void init(SecurityGrantRepository securityGrantRepository) {
        byNameCacheLoader = new CacheLoader<>() {
            @Override
            public Optional<SecurityGrant> load(String key) throws Exception {
                return securityGrantRepository.findOneByName(key);
            }
        };
        bySecSubjectCacheLoader = new CacheLoader<>() {
            @Override
            public List<SecurityGrant> load(Long key) throws Exception {
                return securityGrantRepository.findAllBySubjectId(key);
            }
        };
        this.byNameCache = CacheBuilder.newBuilder().build(byNameCacheLoader);
        this.bySecSubjectCache = CacheBuilder.newBuilder().build(bySecSubjectCacheLoader);
    }

    @Override
    public List<SecurityGrant> findAllBySubjectId(Long subjectId) {
        try {
            return bySecSubjectCache.get(subjectId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SecurityGrant> findOneByName(String name) {
        try {
            return byNameCache.get(name);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void invalidate() {
        this.bySecSubjectCache.invalidateAll();
        this.byNameCache.invalidateAll();
    }
}
