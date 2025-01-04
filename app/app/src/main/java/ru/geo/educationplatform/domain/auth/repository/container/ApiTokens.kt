package ru.geo.educationplatform.domain.auth.repository.container

data class ApiTokens(
    val accessToken: String?,
    val refreshToken: String?
)
