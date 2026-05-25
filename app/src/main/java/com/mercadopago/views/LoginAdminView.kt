package com.mercadopago.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mercadopago.R


@Composable
@Preview(showBackground = true)

    fun LoginAdminView() {

        var passwordVisible by remember {
            mutableStateOf(false)
        }

        var claveSecreta by remember {
            mutableStateOf("")
        }

        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
            Row( modifier = Modifier.fillMaxWidth().padding(20.dp,50.dp),
                horizontalArrangement = Arrangement.Start) {
                Image(painter = painterResource(id = R.drawable.flechaatras)
                    , contentDescription = "",
                    modifier = Modifier.size(40.dp))

            }

            Spacer(modifier = Modifier.height(130.dp))

            Image(
                painter = painterResource(id = R.drawable.admin_access),
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )

            Text(text = "       PANEL DE \n ADMINISTRACION", fontFamily = FontFamily(Font(R.font.changa_medium))
                , fontSize = 20.sp , letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(37.dp))

            OutlinedTextField(
                value = claveSecreta,
                onValueChange = { claveSecreta = it },
                modifier = Modifier.width(360.dp).background(Color(0XFFFCFAFA))
                ,

                placeholder = {
                    Text("Clave secreta", fontFamily= FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                        , color = Color(0,0,0,80)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0,0,0,50),
                    focusedBorderColor = Color(0xFF35C0AB),

                )
                , singleLine = true,
                visualTransformation =     if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector =
                                if (passwordVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,

                            contentDescription = null
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35C0AB))
                , modifier = Modifier.width(360.dp),
                shape = RoundedCornerShape(22.dp)
            )
            {
               Text(text="ACCEDER", fontFamily = FontFamily(Font(R.font.changa_medium))
               , fontSize = 20.sp,
                   letterSpacing = 2.sp
               )

            }



        }
    }
