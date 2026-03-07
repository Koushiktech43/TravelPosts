package com.android.travelposts.presentation.di

import com.android.travelposts.data.repository.GetProductRepository
import com.android.travelposts.domain.usecase.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetProductUseCase(repository: GetProductRepository)  = GetProductsUseCase(repository)
}