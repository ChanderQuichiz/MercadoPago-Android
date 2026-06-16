package com.mercadopago.views

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginSocioView(
  navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val primaryColor = Color(0xFF00C1A2)

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier =  Modifier.height(40.dp))
                IconButton(
                    onClick = {
        navController.navigate("login"){

        }
                    }
                ) {
                    Icon(Icons.Default.Settings,null)
                }
            }
        }
    ) {
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues ),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
            ,    horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(80.dp))

            Surface(
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(50),
                color = Color.White,
                border = BorderStroke(2.dp, primaryColor)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Storefront, null)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "PORTAL DEL SOCIO",
                fontSize = 12.sp,
                color = Color.Gray,
                letterSpacing = 1.5.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "MERCADOPAGO",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("ejemplo@correo.com") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = primaryColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = primaryColor
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("mis-solicitudes")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text(
                    text = "ACCEDER",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "¿No tenés cuenta? ", color = Color.Gray, fontSize = 14.sp)
                TextButton(
                    onClick = {
                        navController.navigate("registro-socio")
                    },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        text = "Registrarte como Socio",
                        color = primaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }


        }
    }

}