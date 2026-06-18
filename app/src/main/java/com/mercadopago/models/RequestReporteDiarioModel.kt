package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class RequestReporteDiarioModel(
    val fecha: String
)
