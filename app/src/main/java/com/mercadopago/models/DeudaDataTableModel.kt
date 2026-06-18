package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class DeudaDataTableModel(
    val estado: String,
    val codigoDeuda: String,
    val emailSocio: String,
    val codigoContrato: String,
    val periodo: String,
    val total: Double
)
