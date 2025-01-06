package ru.geo.educationplatform.domain.greeting.di

import androidx.compose.material3.rememberTooltipState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.geo.educationplatform.domain.auth.repository.JwtStoreRepository
import ru.geo.educationplatform.domain.greeting.api.GreetingApi
import ru.geo.educationplatform.domain.greeting.usecase.GreetingUseCase

@Module
@InstallIn(SingletonComponent::class)
class GreetingModule {
    @Provides
    fun provideGreetingUseCase(
        greetingApi: GreetingApi,
        jwtStoreRepository: JwtStoreRepository
    ): GreetingUseCase {
        return GreetingUseCase(greetingApi, jwtStoreRepository)
    }
}