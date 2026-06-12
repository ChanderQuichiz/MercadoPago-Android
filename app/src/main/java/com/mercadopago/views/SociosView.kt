package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.SocioModel
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.HorizontalDivider
@Composable
fun SociosView(
    navController: NavController
) {

    DetailedDrawer(navController = navController) { padding ->

        var filtroExpandido by remember { mutableStateOf(false) }

        val opcionesEstado = listOf(
            "Todos",
            "Activo",
            "Inactivo"
        )

        var estadoSeleccionado by remember {
            mutableStateOf("Todos")
        }

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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        RoundedCornerShape(15.dp)
                    )
                    .padding(15.dp)
            ) {

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

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            RoundedCornerShape(12.dp)
                        )
                        .border(
                            1.dp,
                            Color.LightGray,
                            RoundedCornerShape(12.dp)
                        )
                )
                {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                filtroExpandido = !filtroExpandido
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = estadoSeleccionado,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontFamily = FontFamily(
                                Font(R.font.changa_medium)
                            )
                        )

                        Text(
                            text =
                                if (filtroExpandido)
                                    "˄"
                                else
                                    "˅",
                            color = Color.Black
                        )
                    }

                    if (filtroExpandido) {

                        HorizontalDivider()

                        listOf(
                            "Todos",
                            "Activo",
                            "Inactivo"
                        ).forEach { opcion ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {

                                        estadoSeleccionado = opcion
                                        filtroExpandido = false
                                    }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    opcion,
                                    color =
                                        if (opcion == estadoSeleccionado)
                                            Color(0xFF355CC0)
                                        else
                                            Color.Black
                                )
                            }

                            if (opcion != "Inactivo") {
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            val sociosFiltrados = socios.filter { socio ->

                val coincideBusqueda =
                    busqueda.isBlank() ||
                            socio.name.contains(
                                busqueda,
                                ignoreCase = true
                            ) ||
                            socio.dni.contains(
                                busqueda,
                                ignoreCase = true
                            )

                val coincideEstado =
                    estadoSeleccionado == "Todos" ||
                            socio.status.equals(
                                estadoSeleccionado,
                                ignoreCase = true
                            )

                coincideBusqueda && coincideEstado
            }
            sociosFiltrados.forEach { socio ->

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
                        .padding(15.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        // FOTO DEL USUARIO
                        Box(
                            modifier = Modifier
                                .width(75.dp)
                                .height(100.dp)
                                .align(Alignment.CenterVertically)
                                .background(
                                    Color(0xFFD9D9D9),
                                    RoundedCornerShape(4.dp)
                                )
                                .border(
                                    1.dp,
                                    Color.LightGray,
                                    RoundedCornerShape(4.dp)
                                )
                        )

                        Spacer(modifier = Modifier.width(15.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            // ESTADO
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(
                                            if (socio.status == "ACTIVO")
                                                Color(0xFF55C5B8)
                                            else
                                                Color.Red,
                                            CircleShape
                                        )
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = socio.status,
                                    color =
                                        if (socio.status == "ACTIVO")
                                            Color(0xFF00E539)
                                        else
                                            Color.Red,
                                    fontFamily = FontFamily(
                                        Font(R.font.changa_medium)
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // DNI Y TELEFONO
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        "DNI",
                                        color = Color.Black,
                                        fontFamily = FontFamily(
                                            Font(R.font.changa_medium)
                                        )
                                    )

                                    Text(
                                        socio.dni,
                                        color = Color.Black
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        "Telefono",
                                        color = Color.Black,
                                        fontFamily = FontFamily(
                                            Font(R.font.changa_medium)
                                        )
                                    )

                                    Text(
                                        socio.phone,
                                        color = Color.Black
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(15.dp))

                            // CONTACTO
                            Text(
                                "Contacto",
                                color = Color.Gray,
                                fontFamily = FontFamily(
                                    Font(R.font.changa_medium)
                                )
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                socio.email,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}