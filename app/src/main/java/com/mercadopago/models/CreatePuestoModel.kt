package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class CreatePuestoModel(
    val id: Int = 0,
    val codigo: String = "",
    val descripcion: String = "",
    val zona: String = "",
    val areaM2: Double = 0.0,
    val precioBaseMensual: Double = 0.0,
    val estado: String = "",
    val servicioIds: List<Int> = emptyList()
)
