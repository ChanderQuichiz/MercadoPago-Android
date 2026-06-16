package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class ServicioModel(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precioMensual: Double,
    val estado: String
)
