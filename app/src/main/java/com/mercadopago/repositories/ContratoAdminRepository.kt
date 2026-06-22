package com.mercadopago.repositories

import com.mercadopago.models.ContratoActivoModel
import com.mercadopago.models.ContratoModel
import com.mercadopago.models.ContratoPendienteModel
import com.mercadopago.services.ContratoAdminService
import com.mercadopago.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ContratoAdminRepository {
    private val api = RetrofitClient.contratoAdmin

    suspend fun getContratosPendientes(): Result<List<ContratoPendienteModel>> = withContext(Dispatchers.IO) {
        try {
            Result.success(api.getContratosPendientes())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getContratosActivos(): Result<List<ContratoActivoModel>> = withContext(Dispatchers.IO) {
        try {
            Result.success(api.getContratosActivos())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createContrato(
        codigoSolicitud: String,
        numeroMeses: Int,
        imagenFile: File,
        mimeType: String
    ): Result<ContratoModel> = withContext(Dispatchers.IO) {
        try {
            val codigoBody = codigoSolicitud.toRequestBody("text/plain".toMediaTypeOrNull())
            val mesesBody = numeroMeses.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val imagenRequestBody = imagenFile.asRequestBody(mimeType.toMediaTypeOrNull())
            val imagenPart = MultipartBody.Part.createFormData(
                "contratoImagen",
                imagenFile.name,
                imagenRequestBody
            )
            Result.success(api.createContrato(codigoBody, mesesBody, imagenPart))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}