package com.mercadopago.models

import kotlinx.serialization.Serializable

@Serializable
data class CreatePuestoModel(
    var id: Number = 0,
    var codigo: String ="",
    var descripcion: String= "",
    var zona: String="",
    var areaM2: Number=0,
    var precioBaseMensual: Number=0,
    var estado: String="",
    var servicioIds: List<Number> = emptyList()
)
