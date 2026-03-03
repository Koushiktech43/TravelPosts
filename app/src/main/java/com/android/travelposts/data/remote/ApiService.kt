package com.android.travelposts.data.remote

import retrofit2.http.GET

interface ApiService {

    @GET("r/travel/top.json")
    suspend fun getPosts() : TravelPostDto
}