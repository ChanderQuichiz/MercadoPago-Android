package com.mercadopago.models

data class SolicitudModel(
    val id: Int = 0,
    val puesto: String = "",
    val codigo: String = "",
    val fecha: String = "",
    val estado: String = "",
    val descripcion: String = ""
)