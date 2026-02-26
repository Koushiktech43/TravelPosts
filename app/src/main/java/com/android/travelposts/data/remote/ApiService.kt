package com.android.travelposts.data.remote

import retrofit2.http.GET

interface ApiService {

    @GET("travel/top.json")
    suspend fun getPosts() : TravelPostDto
}