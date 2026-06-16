package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class SocioModel(
    val id: Int = 0,
    val name: String = "",
    val dni: String = "",
    val email: String = "",
    val phone: String = "",
    val role: String = "",
    val status: String = ""
)
