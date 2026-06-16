package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class PuestoFilterDto(
    val codigo: String,
    val descripcion:String,
    val estado: String,
    val paginator: Paginator
)
