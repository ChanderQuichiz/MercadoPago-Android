package com.mercadopago.models

data class ServicioFilter(
    val nombre: String,
    val descripcion: String,
    val estado: String,
    val paginator: Paginator

)
