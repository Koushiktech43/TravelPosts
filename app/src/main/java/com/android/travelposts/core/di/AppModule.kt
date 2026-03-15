package com.android.travelposts.core.di

import com.android.travelposts.core.DispatcherProvider
import com.android.travelposts.core.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDispatcher() : DispatcherProvider = DispatcherProviderImpl()
}