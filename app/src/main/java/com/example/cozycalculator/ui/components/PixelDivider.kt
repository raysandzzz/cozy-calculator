package com.example.cozycalculator.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Función que separa la expresión del resultado
@Composable
fun PixelDashedDivider(
    color: Color = Color(0xFFFFFDD0),
    thickness: Dp = 2.dp ) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        // [Largo del guión, Largo del espacio]
        val strokeStyle = PathEffect.dashPathEffect(
            intervals = floatArrayOf(32f, 8f),
            phase = 0f
        )

        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = size.height,
            pathEffect = strokeStyle
        )
    }
}