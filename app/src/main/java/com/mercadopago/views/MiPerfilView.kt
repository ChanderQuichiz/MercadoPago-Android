package com.mercadopago.views

import androidx.compose.foundation.Image
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
import com.mercadopago.network.SessionManager
import com.mercadopago.viewmodels.UserViewModel

@Composable
fun MiPerfilView(navController : NavController){

    val me = SessionManager.me
    DetailedDrawer( navController = navController ) { padding ->

        if(me != null) {

            Column(
                modifier = Modifier.fillMaxSize().padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally

            )
            {
                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )
                //header
                //fin header
                //inicio perfil

                Image(
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )

                Text(
                    text = me.name,
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                    letterSpacing = (1.5).sp,


                    )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = me.role,
                    color = Color(0XFF35C0AB),
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                    fontWeight = FontWeight.SemiBold
                )
                //fin perfil
                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )
                //inicio tabla
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
                        .border(1.dp, Color(0XFFA9A8A8), RoundedCornerShape(10.dp))
                        .padding(10.dp, 20.dp)
                ) {
                    //nombre
                    Row(
                        modifier = Modifier.height(35.dp)
                    ) {
                        Text(
                            text = "Nombre completo",
                            fontFamily = FontFamily(Font(R.font.sansationbold)), modifier = Modifier
                                .fillMaxWidth(0.5f)
                        )
                        Text(
                            text = me.name,
                            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                            color = Color(0XFF828080)
                        )
                    }
                    //correo
                    Row(
                        modifier = Modifier
                            .height(35.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Correo electronico",
                            fontFamily = FontFamily(Font(R.font.sansationbold)), modifier = Modifier
                                .fillMaxWidth(0.5f)
                        )
                        Text(
                            text =me.email,
                            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                            color = Color(0XFF828080)

                        )
                    }
                    //telefono
                    Row(
                        modifier = Modifier.height(35.dp)

                    ) {
                        Text(
                            text = "Telefono",
                            fontFamily = FontFamily(Font(R.font.sansationbold)), modifier = Modifier
                                .fillMaxWidth(0.5f)
                        )
                        Text(
                            text = me.phone,
                            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                            color = Color(0XFF828080)

                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Image(
                            painter = painterResource(id = R.drawable.menurow_gray),
                            contentDescription = "",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                    //DNI
                    Row(
                        modifier = Modifier.height(35.dp)
                    ) {
                        Text(
                            text = "DNI",
                            fontFamily = FontFamily(Font(R.font.sansationbold)), modifier = Modifier
                                .fillMaxWidth(0.5f)
                        )
                        Text(
                            text = me.dni,
                            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                            color = Color(0XFF828080)

                        )
                    }
                }
                //fin tabla
            }

        }
}
}