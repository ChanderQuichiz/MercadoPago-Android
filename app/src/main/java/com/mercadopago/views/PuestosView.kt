package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.PuestoCardModel

@Composable
fun PuestosView(navController: NavController) {
    DetailedDrawer(navController = navController) { padding ->

        var state by remember { mutableIntStateOf(0) }
        val titles = listOf("DISPONIBLE", "OCUPADO")

        var puestos: List<PuestoCardModel> by remember {
            mutableStateOf(
                listOf(
                    PuestoCardModel(0, "PUE001", "Puesto de comida", zona = "Zona A", areaM2 = 23, precioBaseMensual = 342.23, "Disponible", listOf(1, 2, 4)),
                    PuestoCardModel(0, "PUE002", "Puesto de comida", zona = "Zona B", areaM2 = 18, precioBaseMensual = 280.00, "Ocupado", listOf(1, 3))
                )
            )
        }

        val puestosFiltrados = when (state) {
            0 -> puestos.filter { it.estado == "Disponible" }
            1 -> puestos.filter { it.estado == "Ocupado" }
            else -> puestos
        }

        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White, RoundedCornerShape(50.dp))
                        .border(1.dp, Color(0xFF35C0AB), RoundedCornerShape(50.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    titles.forEachIndexed { index, title ->
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            color = if (state == index) Color.White else Color.Black,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = if (state == index) Color(0xFF35C0AB) else Color.Transparent,
                                    shape = RoundedCornerShape(50.dp)
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { state = index }
                                .padding(horizontal = 24.dp, vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(modifier = Modifier.padding(10.dp, 0.dp)) {
                    puestosFiltrados.forEach { puesto ->

                        val isDisponible = puesto.estado == "Disponible"

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Store,
                                contentDescription = "Puesto",
                                modifier = Modifier.size(40.dp),
                                tint = if (isDisponible) Color(0xFF35C0AB) else Color.Black
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    puesto.codigo,
                                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    puesto.descripcion,
                                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    listOf(
                                        puesto.zona,
                                        "${puesto.areaM2}m²",
                                        "${puesto.servicios.size} serv."
                                    ).forEach { tag ->
                                        Text(
                                            text = tag,
                                            modifier = Modifier
                                                .background(Color(0xFFEDEDED), RoundedCornerShape(6.dp))
                                                .padding(horizontal = 8.dp, vertical = 4.dp),
                                            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                                            fontSize = 11.sp,
                                            color = Color.DarkGray,
                                            maxLines = 1,
                                            softWrap = false
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = if (isDisponible) "Disponible" else "Ocupado",
                                    fontSize = 10.sp,
                                    color = if (isDisponible) Color(0xFF35C0AB) else Color.Gray,
                                    modifier = Modifier
                                        .background(
                                            color = if (isDisponible) Color(0xFFD4F5EE) else Color(0xFFEEEEEE),
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "S/${puesto.precioBaseMensual}",
                                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp, 100.dp)
                    .size(64.dp)
                    .background(Color(0xFF35C0AB), RoundedCornerShape(16.dp))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { /* TODO: navegar a crear puesto */ }
            ) {
                Icon(
                    imageVector = Icons.Default.AddBusiness,
                    contentDescription = "Agregar puesto",
                    tint = Color.White,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}