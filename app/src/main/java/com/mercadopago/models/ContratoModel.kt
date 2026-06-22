package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class ContratoModel(
    val id: Int,
    val numeroMeses: Int,
    val urlImagen: String,
    val fechaInicio: String,
    val fechaFin: String,
    val montoMensual: Double,
    val estado: String,
    val codigo: String,
    val solicitudId: Int
)