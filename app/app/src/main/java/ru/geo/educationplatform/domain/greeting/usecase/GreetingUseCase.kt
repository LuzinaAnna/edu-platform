package ru.geo.educationplatform.domain.greeting.usecase

import ru.geo.educationplatform.domain.auth.exception.MissingAuthenticationException
import ru.geo.educationplatform.domain.auth.repository.JwtStoreRepository
import ru.geo.educationplatform.domain.greeting.api.GreetingApi
import javax.inject.Inject

class GreetingUseCase @Inject constructor(
    private val greetingApi: GreetingApi,
    private val jwtStoreRepository: JwtStoreRepository
){
    suspend fun greeting(): String {
        val tokens = jwtStoreRepository.get() ?: throw MissingAuthenticationException()
        return greetingApi.greeting(tokens)
    }
}