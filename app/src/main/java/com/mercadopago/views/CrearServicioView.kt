package com.mercadopago.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mercadopago.models.ServicioModel

@Composable
fun CrearServicioView() {

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precioMensual by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("ACTIVO") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 44.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menú",
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "Crear Servicio",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 2.sp
            )
        }

        Spacer(modifier = Modifier.height(46.dp))

        CampoTextoServicio(
            titulo = "NOMBRE DEL SERVICIO",
            valor = nombre,
            placeholder = "Agua, Gas, Electricidad...",
            onValueChange = { nombre = it }
        )

        Spacer(modifier = Modifier.height(34.dp))

        CampoTextoServicio(
            titulo = "DESCRIPCIÓN",
            valor = descripcion,
            placeholder = "Descripción del servicio...",
            onValueChange = { descripcion = it }
        )

        Spacer(modifier = Modifier.height(34.dp))

        CampoTextoServicio(
            titulo = "PRECIO MENSUAL",
            valor = precioMensual,
            placeholder = "0.00",
            onValueChange = { precioMensual = it }
        )

        Spacer(modifier = Modifier.height(34.dp))

        Text(
            text = "ESTADO",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(8.dp))

        EstadoField(
            estado = estado,
            onEstadoChange = { estado = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val servicio = ServicioModel(
                        id = 0,
                        nombre = nombre,
                        descripcion = descripcion,
                        precioMensual = precioMensual.toDoubleOrNull() ?: 0.0,
                        estado = estado
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF27D3BE)
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "Crear",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Button(
                onClick = {
                    nombre = ""
                    descripcion = ""
                    precioMensual = ""
                    estado = "ACTIVO"
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD9D9D9),
                    contentColor = Color.Black
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "Cancelar",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(170.dp))
    }
}

@Composable
fun CampoTextoServicio(
    titulo: String,
    valor: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Text(
        text = titulo,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.5.sp,
                color = Color.DarkGray
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
    )
}

@Composable
fun EstadoField(
    estado: String,
    onEstadoChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = estado,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.5.sp,
                color = DarkGray
            ),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Seleccionar estado"
                    )
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("ACTIVO") },
                onClick = {
                    onEstadoChange("ACTIVO")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text("INACTIVO") },
                onClick = {
                    onEstadoChange("INACTIVO")
                    expanded = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CrearServicioViewPreview() {
    CrearServicioView()
}