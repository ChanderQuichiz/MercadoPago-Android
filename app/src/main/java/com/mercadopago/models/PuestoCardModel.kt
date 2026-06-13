package com.mercadopago.models

import kotlinx.serialization.Serializable
@Serializable
data class PuestoCardModel(
    val id: Int = 0,
    val codigo: String = "",
    val descripcion: String = "",
    val zona: String = "",
    val areaM2: Double = 0.0,
    val precioBaseMensual: Double = 0.0,
    val estado: String = "",
    val servicioIds: List<Int> = emptyList(),
    val servicios: List<ServicioModel> = emptyList()
) {
    val total: Double
        get() = areaM2 * precioBaseMensual +
                servicios.sumOf { it.precioMensual }
}
