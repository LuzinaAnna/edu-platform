package ru.geo.educationplatform.data.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import ru.geo.educationplatform.data.auth.api.AuthenticationApiImpl
import ru.geo.educationplatform.data.auth.repository.JwtStoreRepositoryImpl
import ru.geo.educationplatform.domain.auth.api.AuthenticationApi
import ru.geo.educationplatform.domain.auth.repository.JwtStoreRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthDataModule {
    @Provides
    fun provideAuthenticationApi(
        client: OkHttpClient
    ): AuthenticationApi {
        return AuthenticationApiImpl(client)
    }

    @Provides
    @Singleton
    fun provideJwtStoreRepository(): JwtStoreRepository {
        return JwtStoreRepositoryImpl()
    }
}