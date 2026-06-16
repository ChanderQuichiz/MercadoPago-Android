package com.mercadopago.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarSocioView(
    navController: NavController
) {
    // Variables de estado para retener el texto de cada input del formulario
    var nombresApellidos by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val primaryColor = Color(0xFF00C1A2)
    val backgroundColor = Color(0xFFE5E5E5)
    val textGray = Color(0xFF8E8E93)

    Scaffold(
        topBar = {
            Column() {
                Spacer(modifier =Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = textGray,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Volver al inicio de sesión", color = textGray, fontSize = 12.sp)
                }
            }
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "CREAR CUENTA SOCIO",
                    fontSize = 11.sp,
                    color = textGray,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(text = "MERCADOPAGO", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))

                Spacer(modifier = Modifier.height(20.dp))

                // Input 1: Nombres y Apellidos
                OutlinedTextField(
                    value = nombresApellidos,
                    onValueChange = { nombresApellidos = it },
                    label = { Text("Nombres y apellidos *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = primaryColor, focusedLabelColor = primaryColor)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input 2: DNI con teclado numérico obligado
                OutlinedTextField(
                    value = dni,
                    onValueChange = { dni = it },
                    label = { Text("DNI *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Teclado numérico sin letras
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = primaryColor, focusedLabelColor = primaryColor)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input 3: Teléfono con teclado telefónico
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), // Teclado numérico especial para llamadas
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = primaryColor, focusedLabelColor = primaryColor)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input 4: Correo Electrónico
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = primaryColor, focusedLabelColor = primaryColor)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input 5: Contraseña enmascarada
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = primaryColor, focusedLabelColor = primaryColor)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input 6: Confirmación de contraseña enmascarada
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar contraseña *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = primaryColor, focusedLabelColor = primaryColor)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Botón final para ejecutar la acción de creación de cuenta
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text(text = "REGISTRARSE", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tu solicitud de membresía quedará pendiente de revisión por la administración.",
                    color = textGray,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp
                )
            }
        }
    }
}