package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class EstadoUpdateDto(
    val estado: String
)
