package com.example.cozycalculator.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

// 1. El Estado de la Pantalla
data class CalculatorState(
    val expression: String = "", // Lo que se escribe arriba (ej: "22 + 78")
    val result: String = "0",     // El resultado abajo (ej: "100")
    val isEvaluated: Boolean = false
) {
    val displayExpression: String
        get() = formatExpressionForDisplay(expression)

    val displayResult: String
        get() = formatExpressionForDisplay(result)
}

// 2. Las Acciones que el usuario puede hacer
sealed class CalculatorAction {
    data class Number(val number: String) : CalculatorAction()
    data class Operation(val symbol: String) : CalculatorAction()
    object Clear : CalculatorAction()
    object Delete : CalculatorAction()
    object Calculate : CalculatorAction()
}

// 3. El ViewModel
class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    private val engine = CalculatorEngine()

    fun onAction(action: CalculatorAction) {
        when (action) {
            /* Si el usuario pulsa un número inmediatamente después del =,
             significa que no quiere usar el resultado previo,
             sino empezar una cuenta nueva desde cero, entonces: */
            is CalculatorAction.Number -> {
                _state.update {
                    if (it.isEvaluated) {
                        it.copy(
                            expression = action.number,
                            result = "0",
                            isEvaluated = false
                        )
                    } else {
                        it.copy(
                            expression = it.expression + action.number
                        )
                    }
                }
            }

            is CalculatorAction.Operation -> {
                val symbolToAppend = if (action.symbol == "(" || action.symbol == ")") {
                    action.symbol
                // Añadimos espacios alrededor del operador para que se vea limpio
                } else {
                    " ${action.symbol} "
                }

                _state.update {
                    if (it.isEvaluated && it.result != "Error") {
                        // Reemplaza la expresión previa por el resultado + el nuevo operador
                        it.copy(
                            expression = it.result + symbolToAppend,
                            isEvaluated = false
                        )
                    } else {
                        it.copy(
                            expression = it.expression + symbolToAppend
                        )
                    }
                }
            }

            CalculatorAction.Clear -> {
                _state.update { CalculatorState() } // Resetea a valores por defecto
            }

            CalculatorAction.Delete -> {
                val currentExpr = _state.value.expression
                if (currentExpr.isNotBlank()) {
                    _state.update {
                        it.copy(
                            // Remove the full operator with surrounding spaces if space-terminated
                            expression = if (currentExpr.endsWith(" ")) {
                                currentExpr.dropLast(3)
                            } else {
                                currentExpr.dropLast(1)
                            }
                        )
                    }
                }
            }

            CalculatorAction.Calculate -> {
                val finalResult = engine.evaluate(_state.value.expression)
                _state.update { it.copy(
                    result = finalResult,
                    isEvaluated = true
                )}
            }
        }
    }
}

// Lógica para formatear números altos con comas y hacerlos más legibles
fun formatExpressionForDisplay(expr: String): String {
    if (expr.isBlank()) return ""

    val formatter = DecimalFormat("#,###", DecimalFormatSymbols(Locale.US))

    // Busca números (con o sin decimales)
    val regex = Regex("""\d+(\.\d+)?""")

    // Soporte para solo formatear enteros, es decir después del punto, no formatea.
    return regex.replace(expr) { match ->
        val fullNumber = match.value
        if (fullNumber.contains(".")) {
            val parts = fullNumber.split(".", limit = 2)
            val integerPart = parts[0].toLongOrNull()
            val formattedInteger = if (integerPart != null) formatter.format(integerPart) else parts[0]
            "$formattedInteger.${parts[1]}" // Mantiene los decimales intactos sin comas
        } else {
            val num = fullNumber.toLongOrNull()
            if (num != null) formatter.format(num) else fullNumber
        }
    }
}