package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.DeudaModel
import com.mercadopago.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisDeudasView(navController: NavController) {

    DetailedDrawer(navController = navController) { padding ->

        var tabState by remember { mutableIntStateOf(0) }
        val tabs = listOf("TODOS", "PENDIENTES", "PAGADAS")

        var deudas: List<DeudaModel> by remember {
            mutableStateOf(
                listOf(
                    DeudaModel(1, "DEU-001", "Puesto 1", "Enero 2026", 342.23, "PENDIENTE"),
                    DeudaModel(2, "DEU-002", "Puesto 1", "Febrero 2026", 342.23, "PAGADA"),
                    DeudaModel(3, "DEU-003", "Puesto 2", "Marzo 2026", 520.00, "PENDIENTE")
                )
            )
        }

        val deudasFiltradas = when (tabState) {
            0 -> deudas
            1 -> deudas.filter { it.estado == "PENDIENTE" }
            2 -> deudas.filter { it.estado == "PAGADA" }
            else -> deudas
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(padding)
        ) {

            // 👈 Franja de extremo a extremo
            Text(
                text = "Tu historial de deudas y estado de pagos",
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
                    .verticalScroll(rememberScrollState())
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

                if (deudasFiltradas.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No se encontraron deudas", color = Color.Gray, fontSize = 14.sp)
                    }
                } else {
                    deudasFiltradas.forEach { deuda ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 15.dp)
                                .background(Color(0xFFF6F6F6), RoundedCornerShape(15.dp))
                                .border(1.dp, Color(0xFFD1D1D1), RoundedCornerShape(15.dp))
                        ) {

                            Column(
                                modifier = Modifier.padding(15.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        deuda.codigo,
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        deuda.estado,
                                        color = when (deuda.estado) {
                                            "PAGADA" -> Color(0xFF32CD32)
                                            "PENDIENTE" -> Color(0xFFFF3700)
                                            else -> Color.Gray
                                        },
                                        fontSize = 12.sp,
                                        modifier = Modifier
                                            .background(
                                                color = when (deuda.estado) {
                                                    "PAGADA" -> Color(0xFFD4F7D4)
                                                    "PENDIENTE" -> Color(0xFFFFD6D6)
                                                    else -> Color.Transparent
                                                },
                                                shape = RoundedCornerShape(6.dp)
                                            )
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                Column(
                                    modifier = Modifier.offset(y = (-10).dp)
                                ) {
                                    Text(
                                        deuda.puesto.replace("Puesto ", "PUE-00"),
                                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp,
                                        modifier = Modifier.offset(y = 8.dp)
                                    )
                                    Text(
                                        "Periodo ${deuda.mes.split(" ")[1]} · ${deuda.mes.split(" ")[0]}",
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            Text(
                                "Monto a Pagar: S/${deuda.monto}",
                                color = if (deuda.estado == "PAGADA") Color.Gray else Color(0xFFE87400),
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        if (deuda.estado == "PAGADA") Color(0xFFEEEEEE) else Color(0xFFFFCD9C)
                                    )
                                    .padding(horizontal = 15.dp, vertical = 8.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = { navController.navigate(Screen.ConfirmarPago.route) },
                                    enabled = deuda.estado != "PAGADA",
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF35C0AB),
                                        disabledContainerColor = Color.Gray
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CreditCard,
                                        contentDescription = "Pagar",
                                        modifier = Modifier.size(22.dp),
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "PAGAR",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}