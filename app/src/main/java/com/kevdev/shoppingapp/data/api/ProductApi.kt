package com.kevdev.shoppingapp.data.api

import com.kevdev.shoppingapp.data.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductApi {

     @GET("products")
     suspend fun getAll(): Response<List<Product>>

     @POST("products/save")
     suspend fun save(@Body product: Product): Response<Product>
}