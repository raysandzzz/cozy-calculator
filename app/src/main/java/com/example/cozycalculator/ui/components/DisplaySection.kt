package com.example.cozycalculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cozycalculator.ui.MinecraftFont
import com.example.cozycalculator.ui.displayDayBackgroundColor
import com.example.cozycalculator.ui.displayDayTextColor
import com.example.cozycalculator.ui.displayNightBackgroundColor
import com.example.cozycalculator.ui.displayNightTextColor
import com.example.cozycalculator.ui.pastelRed

@Composable
fun DisplaySection(darkMode: Boolean, expression: String, result: String) {
    val displayColor = if (darkMode) {
        displayNightBackgroundColor
    } else {
        displayDayBackgroundColor
    }

    val displayTextColor = if (darkMode) {
        displayNightTextColor
    } else {
        displayDayTextColor
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .border(width = 4.dp, color = Color(0xFF000000), shape = CutCornerShape(12.dp))
            .background(
                color = Color(displayColor), shape = CutCornerShape(12.dp)
            )
            .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = expression,
                maxLines = 2,
                fontSize = 20.sp,
                fontFamily = MinecraftFont,
                color = Color(displayTextColor)
            )

            PixelDashedDivider()

            Text(
                text = result,
                fontSize = 32.sp,
                fontFamily = MinecraftFont,
                color = if (result == "Error") Color(pastelRed) else Color(displayTextColor)
            )
        }
    }
}