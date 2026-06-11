package com.mercadopago.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Preview(showBackground = true)
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
    val textGray = Color(0xFF8E8E93)

    Scaffold (
        topBar = {
            Column() {
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Volver al inicio de sesión", fontSize = 12.sp)
                }
            }

        }
    ) {
        paddingValues ->
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


            Spacer(modifier = Modifier.height(20.dp))

            Text("CREAR CUENTA SOCIO", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.changa_medium)))
            Text("MERCADOPAGO", fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.changa_medium)))

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .background(Color.White, RoundedCornerShape(30.dp))
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomOutlinedTextField(value = nombres, label = "Nombres y apellidos *") { nombres = it }
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(value = dni, label = "DNI *") { dni = it }
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(value = telefono, label = "Teléfono *") { telefono = it }
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(value = email, label = "Email *") { email = it }
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(value = contrasena, label = "Contraseña *", isPassword = true) { contrasena = it }
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(value = confirmarContrasena, label = "Confirmar contraseña *", isPassword = true) { confirmarContrasena = it }

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {
                        // navController.popBackStack()
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text("REGISTRARSE", fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.changa_medium)))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Tu solicitud de membresía quedará pendiente de revisión por la administración.",
                modifier = Modifier.padding(horizontal = 30.dp),
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                color = Color.Gray,
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CustomOutlinedTextField(value: String, label: String, isPassword: Boolean = false, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(label, fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color(0xFF35C0AB),
            unfocusedContainerColor = Color(0XFFFCFAFA)
        ),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None
    )
}