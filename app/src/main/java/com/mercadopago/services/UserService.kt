package com.mercadopago.services

import com.mercadopago.models.SocioModel
import retrofit2.http.GET

interface UserService {
    @GET("users/me")
    suspend fun getUser(): SocioModel
}
