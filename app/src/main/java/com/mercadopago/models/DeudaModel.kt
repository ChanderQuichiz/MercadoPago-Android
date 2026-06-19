package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class DeudaModel(
    val id: Int? = null,
    val codigo: String,
    val concepto: String? = null,
    val periodo: String,
    val monto: Double? = null,
    val total: Double? = null, // Para DataTable
    val estado: String,
    val contratoId: Int? = null,
    val emailSocio: String? = null, // Para DataTable
    val codigoDeuda: String? = null, // Para DataTable
    val codigoContrato: String? = null // Para DataTable
)
