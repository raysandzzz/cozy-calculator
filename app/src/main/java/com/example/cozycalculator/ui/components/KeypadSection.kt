package com.example.cozycalculator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cozycalculator.R
import com.example.cozycalculator.ui.CalculatorAction
import com.example.cozycalculator.managers.SoundManager

@Composable
fun KeypadSection(
    onAction: (CalculatorAction) -> Unit,
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
                onClick = {
                    onAction(CalculatorAction.Clear)
                    soundManager.playClick(isSoundEnabled) }
            )
            CalculatorButton(
                text = "(",
                normalImageRes = R.drawable.btnparentleftt1,
                pressedImageRes = R.drawable.btnparentleftt2,
                onClick = {
                    onAction(CalculatorAction.Operation("("))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = ")",
                normalImageRes = R.drawable.btnparentright1,
                pressedImageRes = R.drawable.btnparentright2,
                onClick = {
                    onAction(CalculatorAction.Operation(")"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "/",
                normalImageRes = R.drawable.btndiv1,
                pressedImageRes = R.drawable.btndiv2,
                onClick = {
                    onAction(CalculatorAction.Operation("÷"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
        }

        // Fila 2: Números 7, 8, 9 y multiplicación
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            CalculatorButton(
                text = "7",
                normalImageRes = R.drawable.btnsiete1,
                pressedImageRes = R.drawable.btnsiete2,
                onClick = {
                    onAction(CalculatorAction.Number("7"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "8",
                normalImageRes = R.drawable.btnocho1,
                pressedImageRes = R.drawable.btnocho2,
                onClick = {
                    onAction(CalculatorAction.Number("8"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "9",
                normalImageRes = R.drawable.btnnueve1,
                pressedImageRes = R.drawable.btnnueve2,
                onClick = {
                    onAction(CalculatorAction.Number("9"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "*",
                normalImageRes = R.drawable.btnmultit1,
                pressedImageRes = R.drawable.btnmultit2,
                onClick = {
                    onAction(CalculatorAction.Operation("x"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
        }

        // Fila 3: 4, 5, 6, Resta
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            CalculatorButton(
                text = "4",
                normalImageRes = R.drawable.btncuatro1,
                pressedImageRes = R.drawable.btncuatro2,
                onClick = {
                    onAction(CalculatorAction.Number("4"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "5",
                normalImageRes = R.drawable.btncinco1,
                pressedImageRes = R.drawable.btncinco2,
                onClick = {
                    onAction(CalculatorAction.Number("5"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "6",
                normalImageRes = R.drawable.btnseis1,
                pressedImageRes = R.drawable.btnseis2,
                onClick = {
                    onAction(CalculatorAction.Number("6"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "-",
                normalImageRes = R.drawable.btnrest1,
                pressedImageRes = R.drawable.btnrest2,
                onClick = {
                    onAction(CalculatorAction.Operation("-"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
        }

        // Fila 4: 1, 2, 3, Suma
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            CalculatorButton(
                text = "1",
                normalImageRes = R.drawable.btnuno1,
                pressedImageRes = R.drawable.btnuno2,
                onClick = {
                    onAction(CalculatorAction.Number("1"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "2",
                normalImageRes = R.drawable.btndos1,
                pressedImageRes = R.drawable.btndos2,
                onClick = {
                    onAction(CalculatorAction.Number("2"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "3",
                normalImageRes = R.drawable.btntres1,
                pressedImageRes = R.drawable.btntres2,
                onClick = {
                    onAction(CalculatorAction.Number("3"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "+",
                normalImageRes = R.drawable.btnsum1,
                pressedImageRes = R.drawable.btnsum2,
                onClick = {
                    onAction(CalculatorAction.Operation("+"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
        }

        // Fila 5: 0, Punto, Igual
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            CalculatorButton(
                text = "0",
                normalImageRes = R.drawable.btncero1,
                pressedImageRes = R.drawable.btncero2,
                onClick = {
                    onAction(CalculatorAction.Number("0"))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = ".",
                normalImageRes = R.drawable.btndot1,
                pressedImageRes = R.drawable.btndot2,
                onClick = {
                    onAction(CalculatorAction.Operation("."))
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(
                text = "DEL",
                normalImageRes = R.drawable.btndelete1,
                pressedImageRes = R.drawable.btndelete2,
                onClick = {
                    onAction(CalculatorAction.Delete)
                    soundManager.playClick(isSoundEnabled)
                }
            )
            CalculatorButton(text = "=",
                containerColor = Color(0xFF81C784),
                normalImageRes = R.drawable.btnequal1,
                pressedImageRes = R.drawable.btnequal2,
                onClick = {
                    onAction(CalculatorAction.Calculate)
                    soundManager.playClick(isSoundEnabled)
                }
            )
        }
    }
}