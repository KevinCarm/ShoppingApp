package com.kevdev.shoppingapp.data.api

import com.kevdev.shoppingapp.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

     @POST("login")
     suspend fun login(
          @Query("email") email: String,
          @Query("password") password: String
     ): Response<User>

     @POST("register")
     suspend fun register(@Body user: User): Response<User>

     @PUT("users/update/{id}")
     suspend fun update(@Path("id") id: Long, @Body user: User)
}