package com.kevdev.shoppingapp.data.model

data class User(
    var id: Long,
    var name: String,
    var email: String,
    var password: String,
    var image: String?,
    var phone: String?,
    var postcode: String?,
    var city: String?,
    var state: String?,
    var street: String?
) {
    constructor(name: String, email: String, password: String, image: String?, phone: String?, postcode: String?, city: String?, state: String?, street: String?)
            : this(0, name, email, password, image, phone, postcode, city, state, street)
}