package com.mercadopago.navigation

// Definimos la interfaz sellada de manera independiente para que sea accesible por toda la app
sealed interface Destino {
    object Login : Destino    // Ruta del inicio de sesión
    object Registro : Destino // Ruta del formulario de registro
    object Deudas : Destino   // Ruta de la pantalla de deudas
    object Perfil : Destino   // Ruta del perfil de usuario
}