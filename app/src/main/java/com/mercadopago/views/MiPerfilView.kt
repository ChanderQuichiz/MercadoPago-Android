package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.R

@Composable
fun MiPerfilView(navController: NavController, userId: String) {
    DetailedDrawer(navController) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Perfil",
                modifier = Modifier.size(100.dp),
                tint = Color(0xFF35C0AB)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Luis Alexander",
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                letterSpacing = 1.5.sp,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "SOCIO",
                color = Color(0xFF35C0AB),
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .width(360.dp)
                    .padding(2.dp)
            ) {
                Text(
                    text = "INFORMACION PERSONAL",
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Start
                )
            }

            Column(
                modifier = Modifier
                    .background(Color(0xFFF6F6F6), RoundedCornerShape(10.dp))
                    .width(360.dp)
                    .border(1.dp, Color(0xFFA9A8A8), RoundedCornerShape(10.dp))
                    .padding(10.dp, 20.dp)
            ) {
                Row(
                    modifier = Modifier.height(35.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nombre completo",
                        fontFamily = FontFamily(Font(R.font.sansationbold)),
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Text(
                        text = "Luis Alexander",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                        fontSize = 12.sp,
                        color = Color(0xFF828080)
                    )
                }

                Row(
                    modifier = Modifier
                        .height(35.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Correo electronico",
                        fontFamily = FontFamily(Font(R.font.sansationbold)),
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Text(
                        text = "alex@gmail.com",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                        fontSize = 12.sp,
                        color = Color(0xFF828080)
                    )
                }

                Row(
                    modifier = Modifier.height(35.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Telefono",
                        fontFamily = FontFamily(Font(R.font.sansationbold)),
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Text(
                        text = "+51 939473272",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                        fontSize = 12.sp,
                        color = Color(0xFF828080)
                    )
                }

                Row(
                    modifier = Modifier.height(35.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DNI",
                        fontFamily = FontFamily(Font(R.font.sansationbold)),
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Text(
                        text = "62482439",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                        fontSize = 12.sp,
                        color = Color(0xFF828080)
                    )
                }
            }
        }
    }
}