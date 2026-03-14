package com.android.travelposts.data.di

import com.android.travelposts.data.ApiService
import com.android.travelposts.data.repository.GetProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGetProductRepository(apiService: ApiService): GetProductRepository =
        GetProductRepository(apiService)
}