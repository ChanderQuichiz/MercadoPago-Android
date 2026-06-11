package com.mercadopago.models

data class SocioUser(
    val email: String,
    val password: String,
    val nombre: String
)

object UserData {
    fun getSocios(): List<SocioUser> = listOf(
        SocioUser("luis@gmail.com", "123456", "Luis Alexander")
    )
}