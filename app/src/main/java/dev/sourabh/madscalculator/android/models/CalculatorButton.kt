package dev.sourabh.madscalculator.android.models

import dev.sourabh.madscalculator.android.utils.Constants

data class CalculatorButton(
    val text: String,
    val type: Int = Constants.CALCULATOR_BUTTON_TYPE_NUMERICAL
)
