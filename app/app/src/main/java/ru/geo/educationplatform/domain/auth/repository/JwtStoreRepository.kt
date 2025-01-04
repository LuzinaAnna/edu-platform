package ru.geo.educationplatform.domain.auth.repository

import ru.geo.educationplatform.domain.auth.repository.container.ApiTokens

interface JwtStoreRepository {
    fun store(apiTokens: ApiTokens)
    fun get(): ApiTokens?
}