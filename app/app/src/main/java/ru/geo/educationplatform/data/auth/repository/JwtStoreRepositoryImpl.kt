package ru.geo.educationplatform.data.auth.repository

import ru.geo.educationplatform.domain.auth.repository.JwtStoreRepository
import ru.geo.educationplatform.domain.auth.repository.container.ApiTokens
import javax.inject.Singleton

@Singleton
class JwtStoreRepositoryImpl: JwtStoreRepository {
    private var tokens: ApiTokens? = null

    override fun store(apiTokens: ApiTokens) {
        tokens = apiTokens
    }

    override fun get(): ApiTokens? {
        return tokens
    }
}