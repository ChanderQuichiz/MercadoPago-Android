package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class RegistroReporteDiarioModel(
    val emailSocio: String = "",
    val codigoPuesto: String = "",
    val montoRecaudado: Double = 0.0,
    val horaDePago: String = ""
)
