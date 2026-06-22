package com.mercadopago.services

import com.mercadopago.models.ContratoActivoModel
import com.mercadopago.models.ContratoPendienteModel
import retrofit2.http.GET

interface ContratoService {

    @GET("contrato/pendientes")
    suspend fun getContratosPendientes(): List<ContratoPendienteModel>

    @GET("contrato/activos")
    suspend fun getContratosActivos(): List<ContratoActivoModel>
}