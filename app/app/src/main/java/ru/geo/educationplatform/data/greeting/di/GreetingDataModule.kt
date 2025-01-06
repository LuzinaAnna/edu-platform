package ru.geo.educationplatform.data.greeting.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import ru.geo.educationplatform.data.greeting.api.GreetingApiImpl
import ru.geo.educationplatform.domain.greeting.api.GreetingApi

@Module
@InstallIn(SingletonComponent::class)
class GreetingDataModule {
    @Provides
    fun provideGreetingApi(
        client: OkHttpClient
    ): GreetingApi {
        return GreetingApiImpl(client)
    }
}