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


    init {
        searchServicios(ServicioFilter("","","ACTIVO",com.mercadopago.models.Paginator(0,50)))
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