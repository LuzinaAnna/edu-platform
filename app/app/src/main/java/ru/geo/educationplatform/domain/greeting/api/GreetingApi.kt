package ru.geo.educationplatform.domain.greeting.api

import ru.geo.educationplatform.domain.auth.repository.container.ApiTokens

interface GreetingApi {
    suspend fun greeting(tokens: ApiTokens): String
}
