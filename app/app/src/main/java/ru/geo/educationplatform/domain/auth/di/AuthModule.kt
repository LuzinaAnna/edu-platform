package ru.geo.educationplatform.domain.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.geo.educationplatform.domain.auth.AuthenticationManager
import ru.geo.educationplatform.domain.auth.api.AuthenticationApi
import ru.geo.educationplatform.domain.auth.repository.JwtStoreRepository

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    fun provideAuthManager(
        jwtStoreRepository: JwtStoreRepository,
        authenticationApi: AuthenticationApi
    ): AuthenticationManager {
        return AuthenticationManager(jwtStoreRepository, authenticationApi)
    }
}