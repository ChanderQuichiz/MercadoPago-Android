package com.mercadopago.services

import com.mercadopago.models.LoginRequest
import com.mercadopago.models.RegisterSocio
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/login")
    suspend fun sendLogin(loginRequest: LoginRequest): String

    @POST("/auth/logout")
    suspend fun sendLogout(): Void

    @POST("/auth/access-secret")
    suspend fun sendAccessSecret(secretKey: String): String

    @POST("/auth/refresh")
    suspend fun sendRefresh(): String

    @POST("/auth/register")
    suspend fun sendRegister(registerSocio: RegisterSocio): RegisterSocio
}