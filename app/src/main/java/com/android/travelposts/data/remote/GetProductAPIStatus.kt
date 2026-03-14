package com.android.travelposts.data.remote

sealed class GetProductAPIStatus{
    data class Success( val data : ProductDTO) : GetProductAPIStatus()
    data class Error( val message : String) : GetProductAPIStatus()
}


