package com.example.cozycalculator.domain

import androidx.lifecycle.ViewModel
import com.example.cozycalculator.domain.CalculatorEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// 1. El Estado de la Pantalla
data class CalculatorState(
    val expression: String = "", // Lo que se escribe arriba (ej: "22 + 78")
    val result: String = "0"     // El resultado abajo (ej: "100")
)

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
            is CalculatorAction.Number -> {
                _state.update { it.copy(
                    expression = it.expression + action.number
                ) }
            }
            is CalculatorAction.Operation -> {
                val symbolToAppend = if (action.symbol == "(" || action.symbol == ")") {
                    action.symbol
                } else {
                    " ${action.symbol} "
                }
                // Añadimos espacios alrededor del operador para que se vea limpio
                _state.update { it.copy(
                    expression = it.expression + " ${action.symbol} "
                ) }
            }
            CalculatorAction.Clear -> {
                _state.update { CalculatorState() } // Resetea a valores por defecto
            }
            CalculatorAction.Delete -> {
                val currentExpr = _state.value.expression
                if (currentExpr.isNotBlank()) {
                    // Borra el último carácter (manejando los espacios de los operadores si es necesario)
                    _state.update { it.copy(
                        expression = currentExpr.dropLast(1).trimEnd()
                    ) }
                }
            }
            CalculatorAction.Calculate -> {
                val finalResult = engine.evaluate(_state.value.expression)
                _state.update { it.copy(
                    result = finalResult
                )}
            }
        }
    }
}