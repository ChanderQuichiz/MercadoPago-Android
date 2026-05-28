package com.mercadopago.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.components.HeaderComponent

@Composable
fun MiPerfilView(navController: NavController, userId: String){
    DetailedDrawer(navController) { padding ->

        Column(
            modifier = Modifier.fillMaxWidth().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {

            //header
            //fin header
            //inicio perfil

            Image(painter = painterResource(id = R.drawable.perfil),
                contentDescription = "")

            Text(text = "Luis Alexander",
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
            )
            Text(text = "SOCIO",
                color = Color(0XFF35C0AB),
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
            )
            //fin perfil
            //inicio tabla
            Text(text = "INFORMACION PERSONAL",
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
            )
            Column(
                modifier = Modifier.background(Color(0xFFF6F6F6))
            ) {
                //nombre
                Row() {
                    Text(text = "Nombre completo",
                        fontFamily = FontFamily(Font(R.font.sansationbold))

                    )
                    Text(text = "Luis Alexander",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                    )
                }
                //correo
                Row() {
                    Text(text = "Correo electronico",
                        fontFamily = FontFamily(Font(R.font.sansationbold))

                    )
                    Text(text = "alex@gmail.com",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                    )
                }
                //telefono
                Row() {
                    Text(text = "Telefono",
                        fontFamily = FontFamily(Font(R.font.sansationbold))

                    )
                    Text(text = "+51 939473272",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                    )
                    Image(painter = painterResource(id = R.drawable.menurow_gray),
                        contentDescription = ""
                    )
                }
                //DNI
                Row() {
                    Text(text = "DNI",
                        fontFamily = FontFamily(Font(R.font.sansationbold))

                    )
                    Text(text = "62482439",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                    )
                }
            }
            //fin tabla
        }
    }

}