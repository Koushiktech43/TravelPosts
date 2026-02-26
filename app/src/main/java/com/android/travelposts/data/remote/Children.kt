package com.android.travelposts.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Children(
   val child : String,
   val data : ChildrenData
)

@Serializable
data class ChildrenData(
    @SerialName("author_fullname")
    val authorFullName : String,
    val name : String,
    val thumbnail : String
)

