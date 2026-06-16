package com.mercadopago.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.SocioModel
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.UserViewModel

@Composable
fun SociosView(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    DetailedDrawer(navController = navController) { padding ->

        var filtroExpandido by remember { mutableStateOf(false) }

        var estadoSeleccionado by remember {
            mutableStateOf("Todos")
        }

        var busqueda by remember {
            mutableStateOf("")
        }

        val sociosUIState by userViewModel.sociosUIState.collectAsState()

        val estadoParaBackend = when (estadoSeleccionado) {
            "Todos" -> ""
            "Activo" -> "ACTIVO"
            "Inactivo" -> "INACTIVO"
            else -> ""
        }

        LaunchedEffect(busqueda, estadoSeleccionado) {
            userViewModel.cargarSocios(
                query = busqueda,
                status = estadoParaBackend
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
                text = "Lista de Usuarios Registrados",
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
                ) {

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
                            text = if (filtroExpandido) "˄" else "˅",
                            color = Color.Black
                        )
                    }

                    if (filtroExpandido) {

                        HorizontalDivider()

                        listOf("Todos", "Activo", "Inactivo").forEach { opcion ->

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
                                    text = opcion,
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

            when (val state = sociosUIState) {

                is UIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UIState.Success -> {
                    val socios = state.data

                    if (socios.isEmpty()) {
                        Text(
                            text = "No hay socios para mostrar.",
                            color = Color.Gray,
                            fontFamily = FontFamily(
                                Font(R.font.changa_medium)
                            )
                        )
                    } else {
                        socios.forEach { socio ->
                            SocioCard(socio = socio)
                        }
                    }
                }

                is UIState.Error -> {
                    Text(
                        text = "Error: ${state.message}",
                        color = Color.Red,
                        fontFamily = FontFamily(
                            Font(R.font.changa_medium)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SocioCard(
    socio: SocioModel
) {
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

            Image(
                painter = painterResource(id = R.drawable.usuario_generico),
                contentDescription = "Foto de usuario",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(75.dp)
                    .height(100.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                if (socio.status.equals("ACTIVO", ignoreCase = true))
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
                            if (socio.status.equals("ACTIVO", ignoreCase = true))
                                Color(0xFF00E539)
                            else
                                Color.Red,
                        fontFamily = FontFamily(
                            Font(R.font.changa_medium)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "DNI",
                            color = Color.Black,
                            fontFamily = FontFamily(
                                Font(R.font.changa_medium)
                            )
                        )

                        Text(
                            text = socio.dni,
                            color = Color.Black
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Telefono",
                            color = Color.Black,
                            fontFamily = FontFamily(
                                Font(R.font.changa_medium)
                            )
                        )

                        Text(
                            text = socio.phone,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Contacto",
                    color = Color.Gray,
                    fontFamily = FontFamily(
                        Font(R.font.changa_medium)
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = socio.email,
                    color = Color.Black
                )
            }
        }
    }
}
