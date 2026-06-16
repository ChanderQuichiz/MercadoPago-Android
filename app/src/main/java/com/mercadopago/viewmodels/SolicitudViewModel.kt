package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.MiSolicitudModel
import com.mercadopago.repositories.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SolicitudViewModel : ViewModel() {

    private val repository = SolicitudRepository()

    private val _solicitudes = MutableStateFlow<List<MiSolicitudModel>>(emptyList())
    val solicitudes: StateFlow<List<MiSolicitudModel>> = _solicitudes

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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
}