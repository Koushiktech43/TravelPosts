package com.android.travelposts.data.remote

data class ProductDTO(
    val products : List<Product>,
    val total : Int ,
    val skip : Int ,
    val limit : Int
)

data class Product(
    val id : Int,
    val title : String,
    val description : String,
    val category : String,
    val price : Double ,
    val thumbnail : String
)
