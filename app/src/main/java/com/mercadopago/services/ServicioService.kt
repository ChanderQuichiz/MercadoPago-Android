package com.mercadopago.services

import com.mercadopago.models.PageResponse
import com.mercadopago.models.ServicioModel
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ServicioService {


    @GET("servicios/search")
    suspend fun searchServicio(@QueryMap params: Map<String, String>): PageResponse<ServicioModel>

}