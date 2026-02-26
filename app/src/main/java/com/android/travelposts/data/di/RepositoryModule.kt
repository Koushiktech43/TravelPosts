package com.android.travelposts.data.di

import com.android.travelposts.data.remote.ApiService
import com.android.travelposts.data.repository.GetTravelPostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGetTravelRepository(apiService: ApiService) = GetTravelPostRepository(apiService)
}