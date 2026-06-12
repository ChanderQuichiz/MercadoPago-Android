package com.mercadopago.services

import com.mercadopago.models.LoginRequest
import com.mercadopago.models.RegisterSocio
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun sendLogin(@Body loginRequest: LoginRequest): ResponseBody

    @POST("auth/logout")
    suspend fun sendLogout(): Void

    @POST("auth/access-secret")
    suspend fun sendAccessSecret(@Body secretKey: RequestBody): ResponseBody

    @POST("auth/refresh")
    suspend fun sendRefresh(): ResponseBody

    @POST("auth/register")
    suspend fun sendRegister(@Body registerSocio: RegisterSocio): RegisterSocio
}
