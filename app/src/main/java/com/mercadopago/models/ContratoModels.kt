package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class ContratoPendienteModel(
    val codigoSolicitud: String,
    val nombreSocio: String,
    val dniSocio: String,
    val emailSocio: String,
    val phoneSocio: String,
    val codigoPuesto: String,
    val descripcionPuesto: String,
    val zonaPuesto: String,
    val areaM2: Double,
    val servicios: List<String>,
    val montoMensual: Double
)

@Serializable
data class ContratoActivoModel(
    val codigo: String,
    val estado: String,
    val fechaInicio: String,
    val fechaFin: String,
    val montoMensual: Double,
    val email: String,
    val codigoPuesto: String,
    val imagenUrl: String
)