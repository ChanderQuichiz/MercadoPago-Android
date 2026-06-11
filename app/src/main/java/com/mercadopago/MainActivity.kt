package com.mercadopago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.mercadopago.navigation.AppNavigation
import com.mercadopago.ui.theme.MercadoPagoTheme
import com.mercadopago.views.CrearServicioView

import androidx.navigation.compose.rememberNavController
import com.mercadopago.views.CrearPuestoView
import com.mercadopago.views.EnviarSolicitudView
import com.mercadopago.views.PuestosDisponiblesView
import com.mercadopago.views.PuestosView
import com.mercadopago.views.ReportesView
import com.mercadopago.views.ServiciosView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MercadoPagoTheme {
//                AppNavigation()

                val navController = rememberNavController()

//                CrearPuestoView(navController = navController)

                //                PuestosView(navController = navController)

//                CrearServicioView()

//                ServiciosView(navController = navController)

//                PuestosDisponiblesView(navController = navController)

                EnviarSolicitudView()

//                ReportesView()
            }
        }
    }
}