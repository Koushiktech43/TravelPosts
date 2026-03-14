package com.android.travelposts.domain

import com.android.travelposts.data.remote.GetProductAPIStatus
import com.android.travelposts.data.remote.ProductDTO
import com.android.travelposts.data.repository.GetProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
   private val repository: GetProductRepository
) {
    suspend fun invoke() : GetProductAPIStatus {
        return repository.invoke()
    }
}
