package com.mercadopago.models

data class RegisterSocio(
    val email: String,
    val name: String,
    val dni: String,
    val phone: String,
    val password: String,
)
