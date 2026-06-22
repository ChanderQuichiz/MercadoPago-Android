package com.mercadopago.services

import com.mercadopago.models.EstadoUpdateDto
import com.mercadopago.models.MiSolicitudModel
import com.mercadopago.models.SolicitudRequestModel
import com.mercadopago.models.SolicitudResponseModel
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
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

    @GET("solicitudes/pendientes")
    suspend fun getSolicitudesPendientes(): List<SolicitudResponseModel>

    @PUT("solicitudes/{codigo}/estado")
    suspend fun actualizarEstadoSolicitud(
        @Path("codigo") codigo: String,
        @Body dto: EstadoUpdateDto
    ): SolicitudResponseModel
}
