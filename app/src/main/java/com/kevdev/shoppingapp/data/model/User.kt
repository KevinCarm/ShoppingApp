package com.kevdev.shoppingapp.data.model

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val image: String
) {
    constructor(name: String, email: String, password: String, image: String)
            : this(0, name, email, password, image)
}