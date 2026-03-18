package com.android.travelposts.data.repository

import com.android.travelposts.data.ApiService
import com.android.travelposts.data.remote.GetProductAPIStatus
import javax.inject.Inject

class GetProductRepository @Inject constructor (
    private val apiService: ApiService
) {
    suspend fun invoke(limit : Int ,skip : Int) : GetProductAPIStatus {
      return  try {
          GetProductAPIStatus.Success(apiService.getProducts(limit,skip) )
        } catch (e: Exception) {
            e.printStackTrace()
          GetProductAPIStatus.Error(e.message.toString())
        }
    }
}