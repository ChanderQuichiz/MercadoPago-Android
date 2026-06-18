package com.mercadopago.repositories

import com.mercadopago.models.MiSolicitudModel
import com.mercadopago.models.SolicitudRequestModel
import com.mercadopago.models.SolicitudResponseModel
import com.mercadopago.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SolicitudRepository {

    suspend fun createSolicitud(
        razon: String,
        puestoId: Int
    ): Result<SolicitudResponseModel> = withContext(Dispatchers.IO) {
        try {
            Result.success(
                RetrofitClient.solicitud.createSolicitud(
                    SolicitudRequestModel(
                        razon = razon,
                        puestoId = puestoId
                    )
                )
            )
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "No se pudo enviar la solicitud"))
        }
    }

    suspend fun getMisSolicitudes(estado: String? = null): List<MiSolicitudModel> {
        return RetrofitClient.solicitud.getMisSolicitudes(estado)
    }
}
