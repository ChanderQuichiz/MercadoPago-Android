package com.mercadopago.repositories

import com.mercadopago.models.DeudaDataTableModel
import com.mercadopago.models.DeudaModel
import com.mercadopago.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeudaRepository {
    private val api = RetrofitClient.deuda

    suspend fun getDeudasDataTable(): Result<List<DeudaDataTableModel>> = withContext(Dispatchers.IO) {
        try {
            Result.success(api.getDeudasDataTable())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun pagarDeuda(codigo: String): Result<DeudaModel> = withContext(Dispatchers.IO) {
        try {
            Result.success(api.pagarDeuda(codigo))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
