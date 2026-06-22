package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.MiSolicitudModel
import com.mercadopago.viewmodels.SolicitudViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import com.mercadopago.viewmodels.UserViewModel

@Composable
fun MisSolicitudesView(
    navController: NavController,
    userViewModel: UserViewModel,
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    DetailedDrawer(navController = navController, userViewModel = userViewModel) { padding ->

        var tabState by remember { mutableIntStateOf(0) }

        val tabs = listOf(
            "TODOS",
            "PENDIENTE",
            "ACEPTADA",
            "CON\nCONTRATO",
            "RECHAZADA"
        )

        var solicitudExpandida by remember { mutableStateOf<String?>(null) }

        val solicitudes by solicitudViewModel.solicitudes.collectAsState()
        val cargando by solicitudViewModel.cargando.collectAsState()
        val error by solicitudViewModel.error.collectAsState()

        val estadoSeleccionado = when (tabState) {
            0 -> null
            1 -> "PENDIENTE"
            2 -> "ACEPTADA"
            3 -> "CON CONTRATO"
            4 -> "RECHAZADA"
            else -> null
        }

        LaunchedEffect(tabState) {
            solicitudViewModel.cargarMisSolicitudes(estadoSeleccionado)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Historial de Solicitudes de Puestos",
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                color = Color(0xFFA7A7A7),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .border(
                        1.dp,
                        Color(0xFFB7B7B7),
                        RoundedCornerShape(8.dp)
                    )
            ) {
                tabs.forEachIndexed { index, title ->
                    val seleccionado = tabState == index

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .background(
                                if (seleccionado) Color(0xFF43DDD9) else Color.White,
                                if (index == 0) {
                                    RoundedCornerShape(
                                        topStart = 8.dp,
                                        bottomStart = 8.dp
                                    )
                                } else if (index == tabs.lastIndex) {
                                    RoundedCornerShape(
                                        topEnd = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                } else {
                                    RoundedCornerShape(0.dp)
                                }
                            )
                            .border(
                                width = if (index != tabs.lastIndex) 0.5.dp else 0.dp,
                                color = Color(0xFFB7B7B7)
                            )
                            .clickable {
                                tabState = index
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            textAlign = TextAlign.Center,
                            color = if (seleccionado) Color.White else Color.Black,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            when {
                cargando -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {
                    Text(
                        text = "Error: $error",
                        color = Color.Red,
                        fontFamily = FontFamily(Font(R.font.changa_medium))
                    )
                }

                solicitudes.isEmpty() -> {
                    Text(
                        text = "No hay solicitudes para mostrar.",
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.changa_medium))
                    )
                }

                else -> {
                    solicitudes.forEach { solicitud ->
                        SolicitudCard(
                            solicitud = solicitud,
                            expandida = solicitudExpandida == solicitud.codigoSolicitud,
                            onExpandir = {
                                solicitudExpandida =
                                    if (solicitudExpandida == solicitud.codigoSolicitud) {
                                        null
                                    } else {
                                        solicitud.codigoSolicitud
                                    }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SolicitudCard(
    solicitud: MiSolicitudModel,
    expandida: Boolean,
    onExpandir: () -> Unit
) {
    val colorEstado = colorEstado(solicitud.estadoSolicitud)
    val colorFondoEstado = colorEstado.copy(alpha = 0.22f)
    val colorIconoFondo = colorEstado.copy(alpha = 0.22f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp)
            .background(
                Color(0xFFF8F8F8),
                RoundedCornerShape(18.dp)
            )
            .border(
                1.dp,
                Color(0xFFBEBEBE),
                RoundedCornerShape(18.dp)
            )
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        colorIconoFondo,
                        RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "O",
                    color = colorEstado,
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = solicitud.descripcionPuesto,
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        color = Color.Black,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .background(
                                colorFondoEstado,
                                RoundedCornerShape(50.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = solicitud.estadoSolicitud,
                            color = colorEstado,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${solicitud.codigoSolicitud}  •  ${solicitud.fechaSolicitud}  •  ${solicitud.codigoPuesto}",
                    color = Color(0xFFC1C1C1),
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        colorEstado,
                        CircleShape
                    )
                    .border(
                        2.dp,
                        Color.White,
                        CircleShape
                    )
                    .clickable {
                        onExpandir()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (expandida)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expandida)
                        "Contraer solicitud"
                    else
                        "Expandir solicitud",
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }
        }

        if (expandida) {
            Spacer(modifier = Modifier.height(14.dp))

            HorizontalDivider(color = Color(0xFFE0E0E0))

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "DESCRIPCIÓN ENVIADA",
                color = Color(0xFFC2C2C2),
                fontFamily = FontFamily(Font(R.font.changa_medium)),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = solicitud.razon ?: "Sin descripción registrada",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.changa_medium)),
                fontSize = 14.sp
            )
        }
    }
}

fun colorEstado(estado: String): Color {
    return when (estado.uppercase()) {
        "ACEPTADA" -> Color(0xFF39E75F)
        "PENDIENTE" -> Color(0xFFFFB800)
        "CON CONTRATO" -> Color(0xFF3D7BFF)
        "CON_CONTRATO" -> Color(0xFF3D7BFF)
        "RECHAZADA" -> Color(0xFFFF3B30)
        else -> Color.Gray
    }
}