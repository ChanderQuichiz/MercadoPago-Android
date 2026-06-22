package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class DeudaModel(
    val id: Int? = null,
    val codigo: String,
    val concepto: String? = null,
    val periodo: String,
    val monto: Double? = null,
    val total: Double? = null,
    val estado: String,
    val contratoId: Int? = null,
    val emailSocio: String? = null,
    val codigoDeuda: String? = null,
    val codigoContrato: String? = null
)
