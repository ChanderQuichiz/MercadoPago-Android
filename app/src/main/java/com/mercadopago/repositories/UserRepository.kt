package com.mercadopago.repositories

import com.mercadopago.models.SocioModel
import com.mercadopago.models.UserFilterDto
import com.mercadopago.models.toQueryMap
import com.mercadopago.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val user = RetrofitClient.user

    suspend fun getMe(): Result<SocioModel> = withContext(Dispatchers.IO) {
        try {
            Result.success(user.getUser())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchUsers(filter: UserFilterDto): Result<List<SocioModel>> = withContext(Dispatchers.IO) {
        try {
            val response = user.searchUsers(filter.toQueryMap())
            Result.success(response.content)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
