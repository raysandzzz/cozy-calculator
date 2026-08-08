package com.example.cozycalculator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import com.example.cozycalculator.R

// Variable de fuente de texto
val MinecraftFont = FontFamily(
    Font(R.font.minecraft)
)

@Composable
fun CalculatorScreen() {
    // Variable de estado para ver texto en la pantalla
    var displayTextVar by remember { mutableStateOf("0") }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFC3FAC4))
        .padding(top = 64.dp)
        .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Área de texto
        DisplaySection(displayText = displayTextVar)

        // Área donde van los botones
        KeypadSection()

    }

}

@Composable
fun PixelDashedDivider(
    color: Color = Color(0xFFFFFDD0),
    thickness: Dp = 3.dp ) {
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

@Composable
fun DisplaySection(displayText: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .border(width = 4.dp, color = Color(0xFF1B3B32), shape = CutCornerShape(12.dp))
            .background(color = Color(0xFF2C5346), shape = CutCornerShape(12.dp)
            )
            .padding(start = 32.dp, top = 16.dp, end = 32.dp, bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "22 + 78",
                fontSize = 20.sp,
                fontFamily = MinecraftFont,
                color = Color(0xFFF3E5AB)
            )

            PixelDashedDivider()

            Text(
                text = displayText,
                fontSize = 32.sp,
                fontFamily = MinecraftFont,
                color = Color(0xFFF3E5AB)
            )
        }
    }
}

@Composable
fun RowScope.CalculatorButton(
    text: String,                               // Texto a mostrar (ej: "7", "+")
    containerColor: Color = Color(0xFFE0D3C1), // Color de fondo (pastel por defecto)
    contentColor: Color = Color(0xFF2C2C2C),   // Color del texto (oscuro por defecto)
    onClick: () -> Unit = {}                   // Acción al hacer clic
) {
    Box(
        modifier = Modifier
            .weight(1f)        // 1. Estira el botón para llenar su parte de la fila
            .aspectRatio(1f)   // 2. Fuerza a que el alto sea igual al ancho (cuadrado perfecto)
            .border(3.dp, Color(0xFF5C4033), RectangleShape) // 3. Borde retro estilo píxel
            .background(containerColor, RectangleShape)      // 4. Color de fondo
            .clickable { onClick() },                         // 5. Detecta el toque
        contentAlignment = Alignment.Center                    // 6. Centra el texto
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontFamily = MinecraftFont, // Tu fuente retro
            color = contentColor
        )
    }
}

@Composable
fun KeypadSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Fila 1: Botones de control
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(text = "C")
            CalculatorButton(text = "(")
            CalculatorButton(text = ")")
            CalculatorButton(text = "/")
        }
        // Fila 2: Números 7, 8, 9 y multiplicación
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(text = "7")
            CalculatorButton(text = "8")
            CalculatorButton(text = "9")
            CalculatorButton(text = "*")
        }
        // Fila 3: 4, 5, 6, Resta
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(text = "4")
            CalculatorButton(text = "5")
            CalculatorButton(text = "6")
            CalculatorButton(text = "-")
        }

        // Fila 4: 1, 2, 3, Suma
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(text = "1")
            CalculatorButton(text = "2")
            CalculatorButton(text = "3")
            CalculatorButton(text = "+")
        }

        // Fila 5: 0, Punto, Igual
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(text = "0")
            CalculatorButton(text = ".")
            CalculatorButton(text = "⌫") // O el texto "DEL" / "AC"
            CalculatorButton(text = "=", containerColor = Color(0xFF81C784)) // Verde suave
        }
    }
}
