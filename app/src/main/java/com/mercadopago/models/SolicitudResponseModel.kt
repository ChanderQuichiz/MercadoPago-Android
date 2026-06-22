package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class SolicitudResponseModel(
    val id: Int = 0,
    val codigo: String = "",
    val fechaSolicitud: String = "",
    val estado: String = "",
    val razon: String = "",
    val usuarioId: Int = 0,
    val puestoId: Int = 0
)
