package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.MiSolicitudModel
import com.mercadopago.models.SolicitudResponseModel
import com.mercadopago.network.UIState
import com.mercadopago.repositories.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SolicitudViewModel : ViewModel() {

    private val repository = SolicitudRepository()

    private val _solicitudes = MutableStateFlow<List<MiSolicitudModel>>(emptyList())
    val solicitudes: StateFlow<List<MiSolicitudModel>> = _solicitudes

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _createSolicitudState =
        MutableStateFlow<UIState<SolicitudResponseModel>>(UIState.Idle)
    val createSolicitudState: StateFlow<UIState<SolicitudResponseModel>> =
        _createSolicitudState.asStateFlow()

    private val _pendientesState =
        MutableStateFlow<UIState<List<SolicitudResponseModel>>>(UIState.Idle)
    val pendientesState: StateFlow<UIState<List<SolicitudResponseModel>>> =
        _pendientesState.asStateFlow()

    private val _updateState =
        MutableStateFlow<UIState<SolicitudResponseModel>>(UIState.Idle)
    val updateState: StateFlow<UIState<SolicitudResponseModel>> =
        _updateState.asStateFlow()

    fun createSolicitud(razon: String, puestoId: Int) {
        viewModelScope.launch {
            _createSolicitudState.value = UIState.Loading
            repository.createSolicitud(razon, puestoId)
                .onSuccess {
                    _createSolicitudState.value = UIState.Success(it)
                }
                .onFailure {
                    _createSolicitudState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

    fun cargarMisSolicitudes(estado: String? = null) {
        viewModelScope.launch {
            try {
                _cargando.value = true
                _error.value = null

                _solicitudes.value = repository.getMisSolicitudes(estado)

            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar solicitudes"
            } finally {
                _cargando.value = false
            }
        }
    }

    fun cargarSolicitudesPendientes() {
        viewModelScope.launch {
            _pendientesState.value = UIState.Loading
            repository.getSolicitudesPendientes()
                .onSuccess {
                    _pendientesState.value = UIState.Success(it)
                }
                .onFailure {
                    _pendientesState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

    fun responderSolicitud(codigo: String, aceptar: Boolean) {
        viewModelScope.launch {
            _updateState.value = UIState.Loading
            val estado = if (aceptar) "ACEPTADA" else "RECHAZADA"
            repository.actualizarEstadoSolicitud(codigo, estado)
                .onSuccess {
                    _updateState.value = UIState.Success(it)
                    // Recargar la lista después de actualizar
                    cargarSolicitudesPendientes()
                }
                .onFailure {
                    _updateState.value = UIState.Error(it.message ?: "Error al actualizar")
                }
        }
    }

    fun clearUpdateState() {
        _updateState.value = UIState.Idle
    }
}
