package com.android.travelposts.domain

import com.android.travelposts.data.remote.TravelPostDto
import com.android.travelposts.data.repository.GetTravelPostRepository
import javax.inject.Inject

class GetTravelPostUseCase @Inject constructor(
    private val repository : GetTravelPostRepository
) {
    suspend fun invoke()  : TravelPostDto{
        return repository.invoke()
    }

}