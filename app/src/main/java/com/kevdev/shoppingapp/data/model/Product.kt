package com.kevdev.shoppingapp.data.model

data class Product(
     var id: Long,
     var seller: Long,
     var name: String,
     var description: String?,
     var image: String?,
     var price: Double,
     var discount: Float,
     var quantity: Int
) {
     constructor(
          seller: Long,
          name: String,
          description: String?,
          image: String?,
          price: Double,
          discount: Float,
          quantity: Int
     )
             : this(0, seller, name, description, image, price, discount, quantity)
}
