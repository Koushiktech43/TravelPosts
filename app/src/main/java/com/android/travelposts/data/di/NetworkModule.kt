package com.android.travelposts.data.di

import com.android.travelposts.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService() : ApiService {
        return Retrofit.Builder().baseUrl("https://dummyjson.com/products/").addConverterFactory(
            GsonConverterFactory.create()).build().create(ApiService::class.java)
    }
}