package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.SolicitudModel
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon


@Composable
fun MisSolicitudesView(
    navController: NavController
) {
    DetailedDrawer(navController = navController) { padding ->

        var tabState by remember { mutableIntStateOf(0) }

        val tabs = listOf("TODOS", "PENDIENTE", "ACEPTADA", "CON CONTRATO", "RECHAZADA")

        var solicitudExpandida by remember { mutableStateOf<Number?>(null) }

        var solicitudes: List<SolicitudModel> by remember {
            mutableStateOf(
                listOf(
                    SolicitudModel(1, "Puesto 1", "SQL-D12345ED", "2026-05-19", "ACEPTADA", "Texto de ejemplo no se que poner owo"),
                    SolicitudModel(2, "Puesto 2", "SQL-D12345ED", "2026-05-19", "RECHAZADA", "Solicitud rechazada"),
                    SolicitudModel(3, "Puesto 3", "SQL-D12345ED", "2026-05-19", "CON CONTRATO", "Contrato generado")

                )
            )
        }

        val solicitudesFiltradas = when (tabState) {
            0 -> solicitudes
            1 -> solicitudes.filter { it.estado == "PENDIENTE" }
            2 -> solicitudes.filter { it.estado == "ACEPTADA" }
            3 -> solicitudes.filter { it.estado == "CON CONTRATO" }
            4 -> solicitudes.filter { it.estado == "RECHAZADA" }
            else -> solicitudes
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Historial de Solicitudes de Puestos",
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF35C0AB))
                    .padding(12.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tabs.forEachIndexed { index, title ->
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            color = if (tabState == index) Color.White else Color.Gray,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            modifier = Modifier
                                .background(
                                    color = if (tabState == index) Color(0xFF35C0AB) else Color.Transparent,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .border(
                                    1.dp,
                                    if (tabState == index) Color(0xFF35C0AB) else Color(0xFFD1D1D1),
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { tabState = index }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                solicitudesFiltradas.forEach { solicitud ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp)
                            .background(Color(0xFFF6F6F6), RoundedCornerShape(15.dp))
                            .border(1.dp, Color(0xFFD1D1D1), RoundedCornerShape(15.dp))
                            .padding(15.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(solicitud.puesto, fontFamily = FontFamily(Font(R.font.changa_medium)))
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    solicitud.estado,
                                    color = when (solicitud.estado) {
                                        "ACEPTADA" -> Color(0xFF32CD32)
                                        "RECHAZADA" -> Color.Red
                                        else -> Color(0xFFFFC107)
                                    }
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text("${solicitud.codigo} • ${solicitud.fecha}", color = Color.Gray, fontSize = 12.sp)
                            }
                            Icon(
                                imageVector = if (solicitudExpandida == solicitud.id)
                                    Icons.Outlined.KeyboardArrowUp
                                else
                                    Icons.Outlined.KeyboardArrowDown,
                                contentDescription = "Expandir",
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(start = 8.dp)
                                    .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    solicitudExpandida =
                                        if (solicitudExpandida == solicitud.id) null
                                        else solicitud.id
                                }
                            )
                        }

                        if (solicitudExpandida == solicitud.id) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text("DESCRIPCIÓN ENVIADA", color = Color.Gray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(solicitud.descripcion, fontFamily = FontFamily(Font(R.font.changa_medium)))
                        }
                    }
                }
            }
        }
    }
}