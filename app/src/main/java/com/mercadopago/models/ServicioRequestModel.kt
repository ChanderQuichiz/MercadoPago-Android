package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class ServicioRequestModel(
    val nombre: String,
    val descripcion: String,
    val precioMensual: Double,
    val estado: String
)
