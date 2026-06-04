package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.SocioModel

@Composable
fun SociosView(
    navController: NavController
) {

    DetailedDrawer(navController = navController) { padding ->

        var busqueda by remember {
            mutableStateOf("")
        }

        var socios: List<SocioModel> by remember {
            mutableStateOf(
                listOf(
                    SocioModel(
                        1,
                        "Nombre 1",
                        "12345678",
                        "admin@mercadopago.com",
                        "999999999",
                        "Admin",
                        "ACTIVO"
                    ),
                    SocioModel(
                        2,
                        "Nombre 2",
                        "87654321",
                        "admin@mercadopago.com",
                        "988888888",
                        "Socio",
                        "ACTIVO"
                    )
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                "Lista de Usuarios Registrados",
                fontFamily = FontFamily(
                    Font(R.font.inclusivesans_variablefont_wght)
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = busqueda,
                onValueChange = {
                    busqueda = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Buscar por DNI o nombre...")
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            socios.forEach { socio ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(15.dp)
                        )
                        .border(
                            1.dp,
                            Color(0xFFD1D1D1),
                            RoundedCornerShape(15.dp)
                        )
                        .padding(20.dp)
                ) {

                    Text(
                        "Socio",
                        color = Color.Black,
                        fontFamily = FontFamily(
                            Font(R.font.inclusivesans_variablefont_wght)
                        )
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        socio.nombre,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.changa_medium)
                        )

                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        "DNI",
                        color = Color.Black,
                        fontFamily = FontFamily(
                            Font(R.font.inclusivesans_variablefont_wght)
                        )
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        socio.dni,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        "Contacto",
                        color = Color.Black,
                        fontFamily = FontFamily(
                            Font(R.font.inclusivesans_variablefont_wght)
                        )
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        socio.correo,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        socio.telefono,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        "Rol",
                        color = Color.Black,
                        fontFamily = FontFamily(
                            Font(R.font.inclusivesans_variablefont_wght)
                        )
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        socio.rol,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        "Estado",
                        color = Color.Black,
                        fontFamily = FontFamily(
                            Font(R.font.inclusivesans_variablefont_wght)
                        )
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        socio.estado,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color =
                            if (socio.estado == "ACTIVO")
                                Color(0xFF32CD32)
                            else
                                Color.Red
                    )
                }
            }
        }
    }
}