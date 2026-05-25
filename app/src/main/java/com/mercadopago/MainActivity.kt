package com.mercadopago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mercadopago.ui.theme.MercadoPagoTheme
import com.mercadopago.views.MiPerfilView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MercadoPagoTheme {
                MiPerfilView()
            }
        }
    }

}