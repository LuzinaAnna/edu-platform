package ru.geo.educationplatform.domain.auth.api

import ru.geo.educationplatform.domain.auth.repository.container.ApiTokens
import ru.geo.educationplatform.domain.auth.repository.container.Credentials

interface AuthenticationApi {
    suspend fun authenticate(credentials: Credentials): ApiTokens
}
