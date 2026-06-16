package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class MiSolicitudModel(
    val descripcionPuesto: String = "",
    val estadoSolicitud: String = "",
    val codigoSolicitud: String = "",
    val fechaSolicitud: String = "",
    val codigoPuesto: String = "",
    val razon: String? = null
)