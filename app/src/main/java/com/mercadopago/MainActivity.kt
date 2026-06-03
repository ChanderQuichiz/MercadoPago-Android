package com.mercadopago

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import com.mercadopago.ui.theme.MercadoPagoTheme
import com.mercadopago.views.MiPerfilView
import com.mercadopago.views.LoginSocioView
import com.mercadopago.views.RegistrarSocioView
import com.mercadopago.views.MisDeudasView
import com.mercadopago.navigation.Destino

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MercadoPagoTheme {

                val backstack = remember { mutableStateListOf<Destino>(Destino.Login) }

                val destinoActual = backstack.lastOrNull() ?: Destino.Login

                when (destinoActual) {

                    is Destino.Login -> {
                        LoginSocioView(
                            onNavigateToRegister = { backstack.add(Destino.Registro) },
                            onLoginSuccess = {
                                backstack.clear()
                                backstack.add(Destino.Deudas)
                            }
                        )
                    }

                    is Destino.Registro -> {
                        RegistrarSocioView(
                            onNavigateToLogin = { if (backstack.size > 1) backstack.removeLast() },
                            onRegisterSuccess = { if (backstack.size > 1) backstack.removeLast() }
                        )
                    }

                    is Destino.Deudas -> {
                        MisDeudasView()
                    }

                    is Destino.Perfil -> {
                        MiPerfilView()
                    }
                }
            }
        }
    }
}