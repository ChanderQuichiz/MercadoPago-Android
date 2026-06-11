package com.mercadopago

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.mercadopago.navigation.AppNavigation
import com.mercadopago.ui.theme.MercadoPagoTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MercadoPagoTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(
                    color = Color.White,
                    darkIcons = true
                )
                AppNavigation()
            }
        }
    }
}