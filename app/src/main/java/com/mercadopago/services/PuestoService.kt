package com.mercadopago.services

import com.mercadopago.models.PageResponse
import com.mercadopago.models.PuestoCardModel
import com.mercadopago.models.PuestoFilterDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface PuestoService {

    @POST("puestos/create")
    suspend fun createPuesto(@Body createPuestoRequest: PuestoCardModel): PuestoCardModel

    @PUT("puestos/update/{id}")
    suspend fun updatePuesto(@Path("id") id: Int, @Body updatePuestoRequest: PuestoCardModel): PuestoCardModel

    @GET("puestos/search")
    suspend fun searchPuesto(
        @QueryMap params: Map<String, String>
    ): PageResponse<PuestoCardModel>
}