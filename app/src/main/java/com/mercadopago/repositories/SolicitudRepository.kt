package com.mercadopago.repositories

import com.mercadopago.models.EstadoUpdateDto
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

    suspend fun getSolicitudesPendientes(): Result<List<SolicitudResponseModel>> = withContext(Dispatchers.IO) {
        try {
            Result.success(RetrofitClient.solicitud.getSolicitudesPendientes())
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "No se pudieron cargar las solicitudes pendientes"))
        }
    }

    suspend fun actualizarEstadoSolicitud(codigo: String, estado: String): Result<SolicitudResponseModel> = withContext(Dispatchers.IO) {
        try {
            Result.success(RetrofitClient.solicitud.actualizarEstadoSolicitud(codigo, EstadoUpdateDto(estado)))
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "No se pudo actualizar el estado de la solicitud"))
        }
    }
}
