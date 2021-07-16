package dev.sourabh.madscalculator.android.viewmodels

import androidx.lifecycle.ViewModel
import dev.sourabh.madscalculator.android.models.CalculatorButton
import dev.sourabh.madscalculator.android.utils.Constants

class CalculatorActivityViewModel: ViewModel() {

    private val calculatorButtons = listOf(
        CalculatorButton(
            "1"
        ),
        CalculatorButton(
            "2"
        ),
        CalculatorButton(
            "3"
        ),
        CalculatorButton(
            "/",
            Constants.CALCULATOR_BUTTON_TYPE_OPERATOR
        ),CalculatorButton(
            "4"
        ),
        CalculatorButton(
            "5"
        ),
        CalculatorButton(
            "6"
        ),
        CalculatorButton(
            "*",
            Constants.CALCULATOR_BUTTON_TYPE_OPERATOR
        ),CalculatorButton(
            "7"
        ),
        CalculatorButton(
            "8"
        ),
        CalculatorButton(
            "9"
        ),
        CalculatorButton(
            "-",
            Constants.CALCULATOR_BUTTON_TYPE_OPERATOR
        ),CalculatorButton(
            "ANS",
            Constants.CALCULATOR_BUTTON_TYPE_FUNCTION
        ),
        CalculatorButton(
            "0"
        ),
        CalculatorButton(
            "=",
            Constants.CALCULATOR_BUTTON_TYPE_OPERATOR
        ),
        CalculatorButton(
            "+",
            Constants.CALCULATOR_BUTTON_TYPE_OPERATOR
        ),
    )

    fun getCalculatorButtons() = calculatorButtons
}