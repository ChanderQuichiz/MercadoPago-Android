package com.mercadopago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadopago.models.LoginRequest
import com.mercadopago.models.RegisterSocio
import com.mercadopago.network.SessionManager
import com.mercadopago.network.UIState
import com.mercadopago.repositories.AuthRepository
import com.mercadopago.repositories.UserRepository
import com.mercadopago.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val authRepository = AuthRepository()

    private val _loginState = MutableStateFlow<UIState<String>>(UIState.Loading)
    val loginState: StateFlow<UIState<String>> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<UIState<RegisterSocio>>(UIState.Loading)
    val registerState: StateFlow<UIState<RegisterSocio>> = _registerState.asStateFlow()

    private val _logoutSate = MutableStateFlow<UIState<Void>>(UIState.Loading)
    val logoutState: StateFlow<UIState<Void>> = _logoutSate.asStateFlow()

    private val _accessState = MutableStateFlow<UIState<String>>(UIState.Loading)
    val accessState: StateFlow<UIState<String>> = _accessState.asStateFlow()



    fun sendLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _loginState.value = UIState.Loading
            authRepository.sendLogin(loginRequest)
                .onSuccess {
                    _loginState.value = UIState.Success(it)
                }
                .onFailure {
                    _loginState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }

    fun sendRegister(registerSocio: RegisterSocio) {
        viewModelScope.launch {
            _registerState.value = UIState.Loading
            authRepository.sendRegister(registerSocio)
                .onSuccess {
                    _registerState.value = UIState.Success(it)
                }
                .onFailure {
                    _registerState.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }
    }


    fun sendLogout() {
        viewModelScope.launch {
            _logoutSate.value = UIState.Loading
            authRepository.sendLogout()
                .onSuccess {
                    _logoutSate.value = UIState.Success(it)
                }
                .onFailure {
                    _logoutSate.value = UIState.Error(it.message ?: "Error desconocido")
                }
        }

        }


    fun sendAccess(secretKey: String) {
        viewModelScope.launch {
            _accessState.value = UIState.Loading
            authRepository.sendAccess(secretKey)
                .onSuccess {
                    _accessState.value = UIState.Success(it)

                }
                .onFailure {
                    _accessState.value = UIState.Error(it.message ?: "Error desconocido")
                }

        }
    }

}