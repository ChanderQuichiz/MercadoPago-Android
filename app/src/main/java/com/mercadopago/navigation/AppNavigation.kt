package com.mercadopago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mercadopago.views.CrearPuestoView
import com.mercadopago.views.LoginAdminView
import com.mercadopago.views.LoginSocioView
import com.mercadopago.views.MiPerfilView
import com.mercadopago.views.PuestosView
import com.mercadopago.views.MisSolicitudesView
import com.mercadopago.views.RegistrarSocioView
import com.mercadopago.views.SociosView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()   //  Controlador de navegación

    NavHost(
        navController = navController,
        startDestination = Screen.LoginSocio.route     // Primera pantalla
    ) {
        composable(Screen.Login.route) {
            LoginAdminView(navController)
        }
        composable(Screen.Puestos.route) {
            PuestosView(navController)
        }
        composable(Screen.MiPerfil.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            MiPerfilView(navController,userId.toString())
        }
        composable(Screen.CrearPuesto.route) {
            CrearPuestoView(navController)
        }
        composable(Screen.MisSolicitudes.route) {
            MisSolicitudesView(navController)
        }
        composable(Screen.Socios.route) {
            SociosView(navController)
        }
        composable(Screen.LoginSocio.route) {
            LoginSocioView(navController)
        }
        composable(Screen.RegistroSocio.route) {
            RegistrarSocioView(navController)
        }
    }
    }
