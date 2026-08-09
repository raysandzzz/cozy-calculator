package com.example.cozycalculator.ui

import androidx.compose.foundation.Image
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.cozycalculator.R
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.LifecycleEventObserver
import com.example.cozycalculator.domain.CalculatorViewModel
import com.example.cozycalculator.managers.SoundManager
import com.example.cozycalculator.ui.components.DisplaySection
import com.example.cozycalculator.ui.components.KeypadSection

// Color display dia
const val displayDayBackgroundColor = 0xFFB8E0B0
const val displayDayTextColor = 0xFF1B2A1E

// Color display noche
const val displayNightBackgroundColor = 0xFF1B2A1E
const val displayNightTextColor = 0xFFF0EFEA

// Error text color
const val pastelRed = 0xFFFF6B6B

// Variable de fuente de texto
val MinecraftFont = FontFamily(
    Font(R.font.minecraft)
)

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel()
) {

    // Variable de contexto
    val context = LocalContext.current

    // Instancia persistente del gestor de audio
    val soundManager = remember { SoundManager(context) }

    // Estado del interruptor de audio
    var isSoundEnabled by remember { mutableStateOf(true) }

    val state by viewModel.state.collectAsState()

    // 1. Estado para controlar si el modo noche está activo
    var isDarkMode by remember { mutableStateOf(false) }

    // 2. Selección del fondo según el estado
    val backgroundRes = if (isDarkMode) {
        R.drawable.backgroundnight // imagen de fondo nocturno
    } else {
        R.drawable.backgroundday // imagen de fondo diurno
    }

    PlaneDetector(soundManager, isSoundEnabled)

    // Sincronizar música según el estado del switch o al salir de la pantalla
    LaunchedEffect(isSoundEnabled) {
        soundManager.toggleMusic(isSoundEnabled)
    }

    DisposableEffect(Unit) {
        onDispose {
            soundManager.release()
        }
    }

    // --- ICON SWITCH DE AUDIO ---
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
            DisplaySection(
                isDarkMode,
                expression = state.expression,
                result = state.result
            )

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(x = (-4).dp, y = 31.dp),
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
                onAction = viewModel::onAction,
                soundManager = soundManager,
                isSoundEnabled = isSoundEnabled
            )

            Spacer(modifier = Modifier
                .height(16.dp)
            )
        }
    }
}

// Función que escucha cuando la app entra en pausa o regresa a primer plano
@Composable
fun PlaneDetector(soundManager: SoundManager, isSoundEnabled: Boolean) {

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    // Se ejecuta al presionar Home, minimizar o cambiar de app
                    soundManager.toggleMusic(isSoundEnabled = false)
                }
                Lifecycle.Event.ON_RESUME -> {
                    // Se ejecuta al volver a abrir la app
                    if (isSoundEnabled) {
                        soundManager.toggleMusic(isSoundEnabled = true)
                    }
                }
                Lifecycle.Event.ON_DESTROY -> {
                    // Libera los recursos de audio al cerrar la app definitivamente
                    soundManager.release()
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}