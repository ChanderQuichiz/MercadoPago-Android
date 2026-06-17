package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.ServicioFilter
import com.mercadopago.models.ServicioModel
import com.mercadopago.network.UIState
import com.mercadopago.repositories.ServicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServicioViewModel : ViewModel() {
    val servicioRepository = ServicioRepository()

    val _serviciosState = MutableStateFlow<UIState<List<ServicioModel>>>(UIState.Loading)
    val serviciosState: StateFlow<UIState<List<ServicioModel>>> = _serviciosState.asStateFlow()

    private val _getServicioByIdState = MutableStateFlow<UIState<ServicioModel>?>(null)
    val getServicioByIdState: StateFlow<UIState<ServicioModel>?> = _getServicioByIdState.asStateFlow()

    private val _createServicioState = MutableStateFlow<UIState<ServicioModel>?>(null)
    val createServicioState: StateFlow<UIState<ServicioModel>?> = _createServicioState.asStateFlow()

    private val _updateServicioState = MutableStateFlow<UIState<ServicioModel>?>(null)
    val updateServicioState: StateFlow<UIState<ServicioModel>?> = _updateServicioState.asStateFlow()

    init {
        searchServicios(ServicioFilter("","","ACTIVO",com.mercadopago.models.Paginator(0,50)))
    }

    fun getServicioById(id: Int) {
        viewModelScope.launch {
            _getServicioByIdState.value = UIState.Loading
            servicioRepository.getServicioById(id)
                .onSuccess {
                    _getServicioByIdState.value = UIState.Success(it)
                }
                .onFailure {
                    _getServicioByIdState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

    fun createServicio(servicio: ServicioModel) {
        viewModelScope.launch {
            _createServicioState.value = UIState.Loading
            servicioRepository.createServicio(servicio)
                .onSuccess {
                    _createServicioState.value = UIState.Success(it)
                }
                .onFailure {
                    _createServicioState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

    fun updateServicio(id: Int, servicio: ServicioModel) {
        viewModelScope.launch {
            _updateServicioState.value = UIState.Loading
            servicioRepository.updateServicio(id, servicio)
                .onSuccess {
                    _updateServicioState.value = UIState.Success(it)
                }
                .onFailure {
                    _updateServicioState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

    fun searchServicios(servicioFilter: ServicioFilter) {
        viewModelScope.launch {
            _serviciosState.value = UIState.Loading
            servicioRepository.searchServicio(servicioFilter)
                .onSuccess {
                    _serviciosState.value = UIState.Success(it.content)
                }
                .onFailure {
                    _serviciosState.value = UIState.Error(it.message ?: "Error desconocido")
                }

        }
    }

}
