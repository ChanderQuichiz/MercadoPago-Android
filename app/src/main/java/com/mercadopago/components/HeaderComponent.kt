package com.mercadopago.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.mercadopago.R

@Composable
fun HeaderComponent(){
    var titulo by remember { mutableStateOf("") }
    Row( modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(painter = painterResource(id = R.drawable.menu)
            , contentDescription = "",
            modifier = Modifier.size(40.dp)

        )
        Text(text = titulo
            , fontFamily = FontFamily(Font(R.font.changa_medium)))
    }
}