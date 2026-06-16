package com.mercadopago.repositories

import com.mercadopago.models.MiSolicitudModel
import com.mercadopago.network.RetrofitClient

class SolicitudRepository {

    suspend fun getMisSolicitudes(estado: String? = null): List<MiSolicitudModel> {
        return RetrofitClient.solicitud.getMisSolicitudes(estado)
    }
}