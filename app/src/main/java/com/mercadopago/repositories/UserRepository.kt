package com.mercadopago.repositories

import com.mercadopago.models.SocioModel
import com.mercadopago.network.RetrofitClient
import com.mercadopago.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository()  {
    private val user = RetrofitClient.user


    suspend fun getMe():Result<SocioModel> = withContext(Dispatchers.IO){
        try {
            Result.success(user.getUser())
        }catch (e: Exception){
            Result.failure(e)
        }
    }
    }

