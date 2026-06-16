package com.mercadopago.services

import com.mercadopago.models.MiSolicitudModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SolicitudService {

    @GET("solicitudes/mis-solicitudes")
    suspend fun getMisSolicitudes(
        @Query("estado") estado: String? = null
    ): List<MiSolicitudModel>
}