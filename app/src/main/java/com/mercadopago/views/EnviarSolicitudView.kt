package com.mercadopago.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.components.BoxInfo
import com.mercadopago.components.ServicioChip

@Composable
fun EnviarSolicitudView(
    navController: NavController? = null
) {
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp, vertical = 52.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menú",
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(28.dp))

            Text(
                text = "Enviar Solicitud",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 3.sp
            )
        }

        Spacer(modifier = Modifier.height(42.dp))

        Text(
            text = "PUE-002 | COMIDA",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(42.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 22.dp, vertical = 26.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Puesto",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "COMIDA",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))
                Divider(color = Color.Black)

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BoxInfo(text = "Zona A")
                    BoxInfo(text = "10 m²")
                }

                Spacer(modifier = Modifier.height(18.dp))
                Divider(color = Color.Black)

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mensualidad Estimada",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Gray,
                        letterSpacing = 2.sp
                    )

                    Text(
                        text = "$1,120.00",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Color.Black)

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "SERVICIOS ASOCIADOS",
                    fontSize = 19.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Gray,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(22.dp))

                Row {
                    ServicioChip("Agua")
                    Spacer(modifier = Modifier.width(18.dp))
                    ServicioChip("Luz")
                    Spacer(modifier = Modifier.width(18.dp))
                    ServicioChip("Limpieza")
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "MENSAJE PARA LA ADMINISTACIÓN",
            fontSize = 19.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = mensaje,
            onValueChange = { mensaje = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            shape = RoundedCornerShape(10.dp),
            placeholder = {
                Text(
                    text = "Describe brevemente por qué\nquieres este puesto y tu\npropuesta...",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Gray,
                    letterSpacing = 2.sp
                )
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .width(192.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD9D9D9),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Cancelar",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .width(192.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF27D3BE),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Enviar solicitud",
                    fontSize = 17.sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.5.sp
                )
            }
        }
    }
}