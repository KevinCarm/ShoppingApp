package com.kevdev.shoppingapp.data.model

data class Product(
     var id: Long,
     var seller: Long,
     val name: String,
     val description: String?,
     val price: Double,
     val discount: Float,
     var quantity: Int
)
