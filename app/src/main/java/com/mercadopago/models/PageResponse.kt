package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class PageResponse<T> (
    val content: List<T>,
            val totalPages:Int,
    val totalElements:Int,
    val number:Int,
    val size:Int
     )