package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class ServicioModel(
    var id: Number,
    var nombre: String,
    var descripcion: String,
    var precioMensual: Number,
    var estado: String
)