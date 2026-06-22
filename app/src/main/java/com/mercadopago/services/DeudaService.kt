package com.mercadopago.services

import com.mercadopago.models.DeudaDataTableModel
import com.mercadopago.models.DeudaModel
import com.mercadopago.models.MisDeudasModel
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DeudaService {

    @GET("deudas/datatable")
    suspend fun getDeudasDataTable(): List<DeudaDataTableModel>

    @GET("deudas/mis-deudas")
    suspend fun getMisDeudasDataTable(@Query("estado") estado: String? = null): List<MisDeudasModel>

    @PUT("deudas/pagar/{codigo}")
    suspend fun pagarDeuda(@Path("codigo") codigo: String): DeudaModel
}
