package com.android.travelposts.data.repository

import com.android.travelposts.data.remote.ApiService
import com.android.travelposts.data.remote.TravelPostDto
import javax.inject.Inject

class GetTravelPostRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun invoke() : TravelPostDto {
      return  apiService.getPosts()
    }
}