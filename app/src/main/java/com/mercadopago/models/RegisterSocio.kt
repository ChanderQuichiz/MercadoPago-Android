package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterSocio(
    val email: String,
    val name: String,
    val dni: String,
    val phone: String,
    val password: String,
)
