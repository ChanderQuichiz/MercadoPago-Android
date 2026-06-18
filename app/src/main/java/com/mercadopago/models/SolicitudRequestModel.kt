package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class SolicitudRequestModel(
    val razon: String,
    val puestoId: Int
)
