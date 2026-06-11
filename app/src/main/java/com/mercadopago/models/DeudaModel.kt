package com.mercadopago.models

data class DeudaModel(
    val id: Int,
    val codigo: String,
    val puesto: String,
    val mes: String,
    val monto: Double,
    val estado: String
)