package com.example.cozycalculator.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cozycalculator.ui.MinecraftFont

@Composable
fun RowScope.CalculatorButton(
    text: String,
    containerColor: Color = Color(0xFFE0D3C1),
    contentColor: Color = Color(0xFF2C2C2C),
    // Se le pasa una imagen opcionalmente, si no hay, minecraft style se aplica.
    normalImageRes: Int? = null,
    pressedImageRes: Int? = null,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animación física de hundimiento por si el sprite falla de alguna forma
    val offsetY = if (isPressed && normalImageRes == null) 3.dp else 0.dp

    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .offset(y = offsetY)
            // Si hay imágenes/sprites, omitimos el fondo vectorial por defecto
            .then(
                if (normalImageRes == null) {
                    Modifier
                        .border(3.dp, Color(0xFF5C4033), RectangleShape)
                        .background(containerColor, RectangleShape)
                } else Modifier
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null // Quita el halo gris al presionar
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (normalImageRes != null) {
            // OPCIÓN A: Usar Sprites/Imágenes
            val currentImage = if (isPressed && pressedImageRes != null) {
                pressedImageRes
            } else {
                normalImageRes
            }

            Image(
                painter = painterResource(id = currentImage),
                contentDescription = text,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // OPCIÓN B: Diseño vectorial con tipografía Minecraft Font + Hundimiento
            Text(
                text = text,
                fontSize = 24.sp,
                fontFamily = MinecraftFont,
                color = contentColor
            )
        }
    }
}