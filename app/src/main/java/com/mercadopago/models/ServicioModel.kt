package com.mercadopago.models

import android.R
import kotlinx.serialization.Serializable

@Serializable
data class ServicioModel(
    var id: Number,
    var nombre: R.string,
    var descripcion: String,
    var precioMensual: Number,
    var estado: String
) {
}