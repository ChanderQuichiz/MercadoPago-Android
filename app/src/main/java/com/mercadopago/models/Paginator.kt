package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class Paginator (
    val page:Int,
    val size:Int
)