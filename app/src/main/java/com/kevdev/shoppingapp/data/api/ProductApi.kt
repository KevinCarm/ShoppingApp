package com.kevdev.shoppingapp.data.api

import com.kevdev.shoppingapp.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

     @GET("products")
     suspend fun getAll(): Response<List<Product>>
}