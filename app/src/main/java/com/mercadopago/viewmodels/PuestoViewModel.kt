package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.PuestoCardModel
import com.mercadopago.models.PuestoFilterDto
import com.mercadopago.network.UIState
import com.mercadopago.repositories.PuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PuestoViewModel: ViewModel() {

    private val puestoRepository = PuestoRepository()

    private val _puestos = MutableStateFlow<UIState<List<PuestoCardModel>>>(UIState.Loading)

    val puestos: StateFlow<UIState<List<PuestoCardModel>>> = _puestos.asStateFlow()

    private val _puestosDisponibles = MutableStateFlow<UIState<List<PuestoCardModel>>>(UIState.Idle)
    val puestosDisponibles: StateFlow<UIState<List<PuestoCardModel>>> =
        _puestosDisponibles.asStateFlow()


    private val _createPuesto = MutableStateFlow<UIState<PuestoCardModel>>(UIState.Loading)
    val createPuesto: StateFlow<UIState<PuestoCardModel>> = _createPuesto.asStateFlow()


    private val _updatePuesto = MutableStateFlow<UIState<PuestoCardModel>>(UIState.Loading)

    val updatePuesto: StateFlow<UIState<PuestoCardModel>> = _updatePuesto.asStateFlow()


    val _getPuestoById = MutableStateFlow<UIState<PuestoCardModel>>(UIState.Loading)
    val getPuestoById: StateFlow<UIState<PuestoCardModel>> = _getPuestoById.asStateFlow()


    init {
        searchPuesto(PuestoFilterDto("", "", "Disponible", com.mercadopago.models.Paginator(0, 50)))
    }


    fun createPuesto(createPuesto: PuestoCardModel) {
        viewModelScope.launch {
            _createPuesto.value = UIState.Loading
            puestoRepository.createPuesto(createPuesto)
                .onSuccess{
                    _createPuesto.value = UIState.Success(it)
                }
                .onFailure{
                    _createPuesto.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

    fun updatePuesto(id: Int, updatePuesto: PuestoCardModel) {
        viewModelScope.launch {
            _updatePuesto.value = UIState.Loading
            puestoRepository.updatePuesto(id, updatePuesto)
                .onSuccess{
                    _updatePuesto.value = UIState.Success(it)
                }
                .onFailure{
                    _updatePuesto.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
        }

    fun searchPuesto(filter: PuestoFilterDto) {
        viewModelScope.launch {

            _puestos.value = UIState.Loading
            puestoRepository.searchPuesto(filter)
                .onSuccess {
                    _puestos.value = UIState.Success(it.content)
                }
                .onFailure {
                    _puestos.value = UIState.Error(it.message ?: "Error desconocido")
                }

        }
        
    }

    fun getPuestosDisponibles() {
        viewModelScope.launch {
            _puestosDisponibles.value = UIState.Loading
            puestoRepository.getPuestosDisponibles()
                .onSuccess {
                    _puestosDisponibles.value = UIState.Success(it)
                }
                .onFailure {
                    _puestosDisponibles.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }


    fun getPuestoById(id: Int) {
        viewModelScope.launch {
            _getPuestoById.value = UIState.Loading
            puestoRepository.getPuestoById(id)
                .onSuccess {
                    _getPuestoById.value = UIState.Success(it)
                }
                .onFailure {
                    _getPuestoById.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

}


