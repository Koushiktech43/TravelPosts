package com.android.travelposts.data.repository

import com.android.travelposts.data.remote.ApiService
import com.android.travelposts.data.remote.ProductDTO
import javax.inject.Inject

class GetProductRepository  @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun invoke() : List<ProductDTO> {
        return apiService.getProducts()
    }
}