package com.mercadopago.models

data class SocioModel(
    var id: Number = 0,
    var nombre: String = "",
    var dni: String = "",
    var correo: String = "",
    var telefono: String = "",
    var rol: String = "",
    var estado: String = ""
)