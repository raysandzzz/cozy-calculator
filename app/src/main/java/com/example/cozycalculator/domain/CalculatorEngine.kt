package com.example.cozycalculator.domain

import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class CalculatorEngine {

    fun evaluate(expression: String): String {
        return try {
            if (expression.isBlank()) return "0"

            // 1. Limpiamos los símbolos de la UI
            val sanitized = expression
                .replace("X", "*")
                .replace("x", "*")
                .replace("÷", "/")
                .replace(" ", "") // Eliminamos todos los espacios vacíos para que la libreria funcione

            // 2. La librería evalúa respetando jerarquía y paréntesis
            val result = ExpressionBuilder(sanitized).build().evaluate()

            // 3. Formateamos (ej: 5.0 -> 5)
            formatResult(result)
        } catch (e: Exception) {
            "Error"
        }
    }

    private fun formatResult(value: Double): String {
        val symbols = DecimalFormatSymbols(Locale.US)

        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            val df = DecimalFormat("#.######", symbols)
            df.format(value)
        }
    }
}