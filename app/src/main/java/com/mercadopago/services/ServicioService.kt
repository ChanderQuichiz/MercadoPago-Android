package com.mercadopago.services

import com.mercadopago.models.PageResponse
import com.mercadopago.models.ServicioModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ServicioService {

    @GET("servicios/{id}")
    suspend fun getServicioById(@Path("id") id: Int): ServicioModel

    @POST("servicios/create")
    suspend fun createServicio(@Body servicio: ServicioModel): ServicioModel

    @PUT("servicios/update/{id}")
    suspend fun updateServicio(
        @Path("id") id: Int,
        @Body servicio: ServicioModel
    ): ServicioModel

    @GET("servicios/search")
    suspend fun searchServicio(@QueryMap params: Map<String, String>): PageResponse<ServicioModel>
}
