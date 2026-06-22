package com.mercadopago.services

import com.mercadopago.models.MiSolicitudModel
import com.mercadopago.models.SolicitudRequestModel
import com.mercadopago.models.SolicitudResponseModel
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface SolicitudService {

    @POST("solicitudes/create")
    suspend fun createSolicitud(
        @Body request: SolicitudRequestModel
    ): SolicitudResponseModel

    @GET("solicitudes/mis-solicitudes")
    suspend fun getMisSolicitudes(
        @Query("estado") estado: String? = null
    ): List<MiSolicitudModel>
}
