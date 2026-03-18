package com.android.travelposts.data

import com.android.travelposts.data.remote.ProductDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /*
  //  @GET("search")
  //  suspend fun getProducts() : ProductDTO
    */

    @GET("/products")
    suspend fun getProducts(
      @Query("limit") limit : Int ,
      @Query("skip") skip : Int
    ) : ProductDTO
}