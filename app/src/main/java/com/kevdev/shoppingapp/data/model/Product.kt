package com.kevdev.shoppingapp.data.model

data class Product(
     var id: Long,
     var seller: Long,
     val name: String,
     val description: String?,
     val price: Double,
     val discount: Float,
     var quantity: Int
) {
     constructor(
          seller: Long,
          name: String,
          description: String?,
          price: Double,
          discount: Float,
          quantity: Int
     )
             : this(0, seller, name, description, price, discount, quantity)
}
