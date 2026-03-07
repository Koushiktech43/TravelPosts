package com.android.travelposts.domain.usecase

import com.android.travelposts.data.remote.ProductDTO
import com.android.travelposts.data.repository.GetProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: GetProductRepository
) {
    suspend fun invoke() : List<ProductDTO> {
        return repository.invoke()
    }
}