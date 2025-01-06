package ru.geo.educationplatform.domain.auth.repository.container

enum class AuthenticationStatus {
    SERVER_ERROR,
    INVALID_CREDENTIALS,
    SUCCESSFUL,
    PROCESSING,
    DUMMY
}