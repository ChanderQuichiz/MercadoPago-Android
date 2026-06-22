package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class MisDeudasModel(
    val codigoDeuda: String,
    val codigoPuesto: String,
    val periodo: String,
    val monto: Double,
    val estado: String,
    val servicios: List<String>
)