package com.mercadopago.repositories

import com.mercadopago.models.LoginRequest
import com.mercadopago.models.RegisterSocio
import com.mercadopago.network.RetrofitClient
import com.mercadopago.network.SessionManager
import com.mercadopago.services.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class AuthRepository(private val authService: AuthService? = null) {
    
    private val auth: AuthService by lazy {
        authService ?: RetrofitClient.auth
    }

    suspend fun sendLogin(loginRequest: LoginRequest): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = auth.sendLogin(loginRequest)
            val token = response.string().trim().removeSurrounding("\"")

            SessionManager.accessToken = token
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendRegister(registerSocio: RegisterSocio): Result<RegisterSocio> =
        withContext(Dispatchers.IO) {
            try {
                Result.success(auth.sendRegister(registerSocio))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun sendAccess(secretKey: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val body = secretKey.toRequestBody("text/plain".toMediaType())
            val response = auth.sendAccessSecret(body)
            val token = response.string().trim().removeSurrounding("\"")

            SessionManager.accessToken = token
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendLogout(): Result<Void> = withContext(Dispatchers.IO) {
        try {
            Result.success(auth.sendLogout())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendRefresh(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = auth.sendRefresh()
            val token = response.string()
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun refreshToken(): String {
        val response = auth.sendRefresh()
        val token = response.string().trim().removeSurrounding("\"")

        SessionManager.accessToken = token
        return token
    }
}
