package com.mercadopago.services

import com.mercadopago.models.DeudaDataTableModel
import com.mercadopago.models.DeudaModel
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface DeudaService {

    @GET("deudas/datatable")
    suspend fun getDeudasDataTable(): List<DeudaDataTableModel>

    @PUT("deudas/pagar/{codigo}")
    suspend fun pagarDeuda(@Path("codigo") codigo: String): DeudaModel
}
