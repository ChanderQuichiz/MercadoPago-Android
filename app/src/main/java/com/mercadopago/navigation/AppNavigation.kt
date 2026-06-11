package com.mercadopago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mercadopago.views.CrearPuestoView
import com.mercadopago.views.LoginAdminView
import com.mercadopago.views.MiPerfilView
import com.mercadopago.views.PuestosView
import com.mercadopago.views.MisSolicitudesView
import com.mercadopago.views.MisDeudasView
import com.mercadopago.views.SociosView
import com.mercadopago.views.ConfirmarPagoView
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.AnimatedContentTransitionScope
import com.mercadopago.views.LoginSocioView
import com.mercadopago.views.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route

    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginAdminView(navController)
        }
        composable(Screen.LoginSocio.route) {
            LoginSocioView(
                onNavigateToRegister = { },
                onLoginSuccess = {
                    navController.navigate(Screen.MisSolicitudes.route){
                        popUpTo(Screen.LoginSocio.route) { inclusive = true }
                    }
                }
            )
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
        composable(Screen.Deudas.route) {
            MisDeudasView(navController)
        }
        composable(Screen.ConfirmarPago.route) {
            ConfirmarPagoView(navController)
        }
        composable(Screen.MisSolicitudes.route) {
            MisSolicitudesView(navController)
        }
        composable(Screen.Socios.route) {
            SociosView(navController)
        }
        composable(
            route = Screen.ConfirmarPago.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it })
            }
        ) {
            ConfirmarPagoView(navController)
        }
    }
}