package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.SolicitudModel

@Composable
fun MisSolicitudesView(
    navController: NavController
) {

    DetailedDrawer(navController = navController) { padding ->

        var tabState by remember {
            mutableIntStateOf(0)
        }

        val tabs = listOf(
            "TODOS",
            "PENDIENTE",
            "ACEPTADA",
            "CON CONTRATO",
            "RECHAZADA"
        )

        var solicitudExpandida by remember {
            mutableStateOf<Number?>(null)
        }

        var solicitudes: List<SolicitudModel> by remember {
            mutableStateOf(
                listOf(
                    SolicitudModel(
                        1,
                        "Puesto 1",
                        "SQL-D12345ED",
                        "2026-05-19",
                        "ACEPTADA",
                        "Texto de ejemplo no se que poner owo"
                    ),
                    SolicitudModel(
                        2,
                        "Puesto 2",
                        "SQL-D12345ED",
                        "2026-05-19",
                        "RECHAZADA",
                        "Solicitud rechazada"
                    ),
                    SolicitudModel(
                        3,
                        "Puesto 3",
                        "SQL-D12345ED",
                        "2026-05-19",
                        "CON CONTRATO",
                        "Contrato generado"
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
                text = "Historial de Solicitudes de Puestos",
                fontFamily = FontFamily(
                    Font(R.font.inclusivesans_variablefont_wght)
                ),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            SecondaryTabRow(
                selectedTabIndex = tabState
            ) {

                tabs.forEachIndexed { index, title ->

                    Tab(
                        selected = tabState == index,
                        onClick = {
                            tabState = index
                        },
                        text = {
                            Text(
                                title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            solicitudes.forEach { solicitud ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                        .background(
                            Color(0xFFF6F6F6),
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                solicitud.puesto,
                                fontFamily = FontFamily(
                                    Font(R.font.changa_medium)
                                )
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                solicitud.estado,
                                color =
                                    when (solicitud.estado) {

                                        "ACEPTADA" ->
                                            Color(0xFF32CD32)

                                        "RECHAZADA" ->
                                            Color.Red

                                        else ->
                                            Color(0xFFFF5722)
                                    }
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                "${solicitud.codigo} • ${solicitud.fecha}",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }

                        Text(
                            if (solicitudExpandida == solicitud.id)
                                "˄"
                            else
                                "˅",
                            fontSize = 25.sp,
                            modifier = Modifier.clickable {

                                solicitudExpandida =
                                    if (solicitudExpandida == solicitud.id)
                                        null
                                    else
                                        solicitud.id
                            }
                        )
                    }

                    if (solicitudExpandida == solicitud.id) {

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            "DESCRIPCIÓN ENVIADA",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            solicitud.descripcion,
                            fontFamily = FontFamily(
                                Font(R.font.changa_medium)
                            )
                        )
                    }
                }
            }
        }
    }
}
