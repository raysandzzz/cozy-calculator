package com.example.cozycalculator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
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

// Color display dia
val displayDayBackgroundColor = 0xFFB8E0B0
val displayDayTextColor = 0xFF1B2A1E

// Color display noche
val displayNightBackgroundColor = 0xFF1B2A1E
val displayNightTextColor = 0xFFF0EFEA

@Composable
fun CalculatorScreen() {
    // Variable de estado para ver texto en la pantalla
    var displayTextVar by remember { mutableStateOf("0") }

    // 1. Estado para controlar si el modo noche está activo
    var isDarkMode by remember { mutableStateOf(false) }

    // 2. Selección del fondo según el estado
    val backgroundRes = if (isDarkMode) {
        R.drawable.backgroundnight // Tu imagen de fondo nocturno
    } else {
        R.drawable.backgroundday // Tu imagen de fondo diurno
    }

    val context = LocalContext.current

    // Instancia persistente del gestor de audio
    val soundManager = remember { SoundManager(context) }

    // Estado del interruptor de audio
    var isSoundEnabled by remember { mutableStateOf(true) }

    // Sincronizar música según el estado del switch o al salir de la pantalla
    LaunchedEffect(isSoundEnabled) {
        soundManager.toggleMusic(isSoundEnabled)
    }

    DisposableEffect(Unit) {
        onDispose {
            soundManager.release()
        }
    }

    // --- EN TU ICONO / SWITCH DE AUDIO ---
    IconButton(onClick = { isSoundEnabled = !isSoundEnabled }) {
        Text(text = if (isSoundEnabled) "🔊" else "🔇", fontSize = 20.sp)
    }

    // Box para poder aplicar un background
    Box(modifier = Modifier
        .fillMaxSize()
        ) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // --- EN TU ICONO / SWITCH DE AUDIO ---
                IconButton(onClick = { isSoundEnabled = !isSoundEnabled }) {
                    Text(text = if (isSoundEnabled) "🔊" else "🔇", fontSize = 20.sp)
                }

                Switch(
                    checked = isSoundEnabled,
                    onCheckedChange = { isSoundEnabled = it }
                )

                Spacer(modifier = Modifier.width(8.dp))


                Text(
                    text = if (isDarkMode) "🌙" else "☀️",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isDarkMode = it }
                )

                Spacer(modifier = Modifier
                    .height(64.dp)
                )

            }

            // Área de texto/numeros
            DisplaySection(displayTextVar, isDarkMode)

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(x = -4.dp, y = 31.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.android),
                    contentDescription = "Android logo",
                    filterQuality = FilterQuality.None,
                    modifier = Modifier.size(160.dp)
                )
            }

            // Área donde van los botones
            KeypadSection(
                soundManager = soundManager,
                isSoundEnabled = isSoundEnabled
            )

            Spacer(modifier = Modifier
                .height(16.dp)
            )

        }
    }
}

// Funcion que crea la linea separadora en la pantalla de numeros
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

@Composable
fun DisplaySection(displayText: String, darkMode: Boolean) {
    val display_color = if (darkMode) {
        displayNightBackgroundColor
    } else {
        displayDayBackgroundColor
    }

    val display_text_color = if (darkMode) {
        displayNightTextColor
    } else {
        displayDayTextColor
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .border(width = 4.dp, color = Color(0xFF000000), shape = CutCornerShape(12.dp))
            .background(color = Color(display_color), shape = CutCornerShape(12.dp)
            )
            .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 16.dp)
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
                color = Color(display_text_color)
            )

            PixelDashedDivider()

            Text(
                text = displayText,
                fontSize = 32.sp,
                fontFamily = MinecraftFont,
                color = Color(display_text_color)
            )
        }
    }

    Spacer(modifier = Modifier
        .height(32.dp)
    )
}

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

    // Animación física de hundimiento para cuando NO usas sprite completo
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
            // OPCIÓN B: Tu diseño vectorial con tipografía Minecraft Font + Hundimiento
            Text(
                text = text,
                fontSize = 24.sp,
                fontFamily = MinecraftFont,
                color = contentColor
            )
        }
    }
}

@Composable
fun KeypadSection(
    soundManager: SoundManager,
    isSoundEnabled: Boolean
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 2.dp, start = 16.dp, bottom = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Fila 1: Botones de control
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            CalculatorButton(
                text = "C",
                normalImageRes = R.drawable.btnc1,
                pressedImageRes = R.drawable.btnc2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "(",
                normalImageRes = R.drawable.btnparentleftt1,
                pressedImageRes = R.drawable.btnparentleftt2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = ")",
                normalImageRes = R.drawable.btnparentright1,
                pressedImageRes = R.drawable.btnparentright2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "/",
                normalImageRes = R.drawable.btndiv1,
                pressedImageRes = R.drawable.btndiv2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
        }

        // Fila 2: Números 7, 8, 9 y multiplicación
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            CalculatorButton(
                text = "7",
                normalImageRes = R.drawable.btnsiete1,
                pressedImageRes = R.drawable.btnsiete2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "8",
                normalImageRes = R.drawable.btnocho1,
                pressedImageRes = R.drawable.btnocho2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "9",
                normalImageRes = R.drawable.btnnueve1,
                pressedImageRes = R.drawable.btnnueve2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "*",
                normalImageRes = R.drawable.btnmultit1,
                pressedImageRes = R.drawable.btnmultit2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
        }

        // Fila 3: 4, 5, 6, Resta
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            CalculatorButton(
                text = "4",
                normalImageRes = R.drawable.btncuatro1,
                pressedImageRes = R.drawable.btncuatro2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "5",
                normalImageRes = R.drawable.btncinco1,
                pressedImageRes = R.drawable.btncinco2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "6",
                normalImageRes = R.drawable.btnseis1,
                pressedImageRes = R.drawable.btnseis2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "-",
                normalImageRes = R.drawable.btnrest1,
                pressedImageRes = R.drawable.btnrest2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
        }

        // Fila 4: 1, 2, 3, Suma
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            CalculatorButton(
                text = "1",
                normalImageRes = R.drawable.btnuno1,
                pressedImageRes = R.drawable.btnuno2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "2",
                normalImageRes = R.drawable.btndos1,
                pressedImageRes = R.drawable.btndos2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "3",
                normalImageRes = R.drawable.btntres1,
                pressedImageRes = R.drawable.btntres2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "+",
                normalImageRes = R.drawable.btnsum1,
                pressedImageRes = R.drawable.btnsum2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
        }

        // Fila 5: 0, Punto, Igual
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            CalculatorButton(
                text = "0",
                normalImageRes = R.drawable.btncero1,
                pressedImageRes = R.drawable.btncero2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = ".",
                normalImageRes = R.drawable.btndot1,
                pressedImageRes = R.drawable.btndot2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "DEL",
                normalImageRes = R.drawable.btndelete1,
                pressedImageRes = R.drawable.btndelete2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )

            CalculatorButton(text = "=",
                containerColor = Color(0xFF81C784),
                normalImageRes = R.drawable.btnequal1,
                pressedImageRes = R.drawable.btnequal2,
                onClick = { soundManager.playClick(isSoundEnabled) }
            )
        }
    }
}
