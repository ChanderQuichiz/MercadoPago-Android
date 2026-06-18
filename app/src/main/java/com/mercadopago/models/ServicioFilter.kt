package com.mercadopago.models

data class ServicioFilter(
    val query: String,
    val estado: String,
    val paginator: Paginator
)
