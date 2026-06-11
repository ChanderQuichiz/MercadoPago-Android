package com.mercadopago.repositories

import com.mercadopago.models.LoginRequest
import com.mercadopago.models.RegisterSocio
import com.mercadopago.network.RetrofitClient
import com.mercadopago.network.SessionManager
import com.mercadopago.services.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val authService: AuthService)
{
    private val auth = RetrofitClient.auth

    suspend fun sendLogin(loginRequest: LoginRequest):Result<String> = withContext(Dispatchers.IO){
        try {
            Result.success(auth.sendLogin(loginRequest))
        }catch (e: Exception){
            Result.failure(e)
        }
    }
    suspend fun sendRegister(registerSocio: RegisterSocio):Result<RegisterSocio> = withContext(Dispatchers.IO){
        try {
            Result.success(auth.sendRegister(registerSocio))
        }catch (e: Exception){
            Result.failure(e)
        }
    }
    suspend fun  sendAccess(secretKey: String): Result<String> = withContext(Dispatchers.IO){
           try {
               Result.success(auth.sendAccessSecret(secretKey))
           }catch (e: Exception) {
               Result.failure(e)
           }
    }

    suspend fun sendLogout(): Result<Void> = withContext(Dispatchers.IO){
        try {
            Result.success(auth.sendLogout())
        }catch (e: Exception){
            Result.failure(e)
        }
    }
    suspend fun sendRefresh(): Result<String> = withContext(Dispatchers.IO){
        try {
            Result.success(auth.sendRefresh())
        }catch (e: Exception){
            Result.failure(e)
        }
    }


    suspend fun refreshToken(): String {

        val response =
            authService.sendRefresh()

        SessionManager.accessToken =
            response
        return response
    }


    }


