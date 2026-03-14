package com.android.travelposts.data.remote

data class ProductDTO(
    val products : List<Product>
)

data class Product(
    val id : Int,
    val title : String,
    val description : String,
    val price : Double ,
    val thumbnail : String
)
