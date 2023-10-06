package com.example.textbrush

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextBrushScreen() {
    var textSize by remember { mutableStateOf(30f.sp) }
    var drawnPaths by remember { mutableStateOf(listOf<List<OffsetWithAngle>>()) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        TextBrushCanvas(
            modifier = Modifier.fillMaxSize(),
            style = TextStyle(fontSize = textSize, color = Color.Black)
        )

        Column(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                if (drawnPaths.isNotEmpty()) {
                    drawnPaths = drawnPaths.dropLast(1)
                }
            }) {
                Text("Undo")
            }
        }
    }
}