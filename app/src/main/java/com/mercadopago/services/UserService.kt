package com.mercadopago.services

import com.mercadopago.models.PageResponse
import com.mercadopago.models.SocioModel
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface UserService {

    @GET("users/me")
    suspend fun getUser(): SocioModel

    @GET("users/search")
    suspend fun searchUsers(
        @QueryMap params: Map<String, String>
    ): PageResponse<SocioModel>
}
