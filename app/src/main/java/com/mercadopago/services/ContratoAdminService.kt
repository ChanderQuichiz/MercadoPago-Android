package com.mercadopago.services

import com.mercadopago.models.ContratoActivoModel
import com.mercadopago.models.ContratoModel
import com.mercadopago.models.ContratoPendienteModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ContratoAdminService {

    @GET("contrato/pendientes")
    suspend fun getContratosPendientes(): List<ContratoPendienteModel>

    @GET("contrato/activos")
    suspend fun getContratosActivos(): List<ContratoActivoModel>

    @Multipart
    @POST("contrato/create")
    suspend fun createContrato(
        @Part("codigoSolicitud") codigoSolicitud: RequestBody,
        @Part("numeroMeses") numeroMeses: RequestBody,
        @Part contratoImagen: MultipartBody.Part
    ): ContratoModel
}