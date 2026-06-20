package com.mercadopago.models

import kotlinx.serialization.Serializable
import java.time.LocalDate
@Serializable
data class MisPuestoDto(
    val nombrePuesto: String,
    val codigoPuesto: String,
    val zonaPuesto: String,
    val areaM2: Double,
    val fechaFinContrato: String,
    val montoMensual: Double,
    val servicios: List<String>,
    val codigoContrato: String,
    val fechaInicioContrato: String
)
