package com.mercadopago.models

data class UserFilterDto(
    val query: String = "",
    val role: String = "USER",
    val status: String = "",
    val page: Int = 0,
    val size: Int = 50
)