package com.android.travelposts.data

import com.android.travelposts.data.remote.ProductDTO
import retrofit2.http.GET

interface ApiService {

    @GET("search")
    suspend fun getProducts() : ProductDTO
}