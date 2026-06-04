package com.mercadopago.models

data class SolicitudModel(
    var id: Number = 0,
    var puesto: String = "",
    var codigo: String = "",
    var fecha: String = "",
    var estado: String = "",
    var descripcion: String = ""
)