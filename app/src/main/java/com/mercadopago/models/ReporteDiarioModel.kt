package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class ReporteDiarioModel(
    val totalRecaudado: Double = 0.0,
    val listadoRecaudacion: List<RegistroReporteDiarioModel> = emptyList()
)
