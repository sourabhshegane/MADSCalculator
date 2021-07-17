package dev.sourabh.madscalculator.android.viewmodels

import androidx.lifecycle.ViewModel
import dev.sourabh.madscalculator.android.models.CalculatorButton
import dev.sourabh.madscalculator.android.models.Operation
import dev.sourabh.madscalculator.android.repository.CalculatorActivityRepository
import dev.sourabh.madscalculator.android.utils.Constants

class CalculatorActivityViewModel : ViewModel() {

    private val repository = CalculatorActivityRepository()

    init {
        repository.getPreviousOperations()
    }

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
        ), CalculatorButton(
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
        ), CalculatorButton(
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
        ), CalculatorButton(
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
        )
    )


    private val operationsHistory = mutableListOf<Operation>()

    fun getLatestOperations() = repository.getNewOperationsLiveData()

    fun getCalculatorButtons() = calculatorButtons

    fun getPreviousResult(): Int {
        if(operationsHistory != null && operationsHistory.isNotEmpty())
            return operationsHistory[operationsHistory.lastIndex].result
        return -1
    }

    fun addOperationToOperationHistory(expression: String, result: Int) {
        val operation = Operation(
            expression,
            result
        )
        //operationsHistory.add(operation)
        repository.saveOperationToDatabase(operation)
    }

    fun addOperationsToOperationHistory(latestOperations: List<Operation>){
        operationsHistory.clear()
        operationsHistory.addAll(latestOperations)
    }

    fun getOperationsHistory(): List<Operation> {
        return if (operationsHistory.size >= 10)
            operationsHistory.reversed().subList(0, 9)
        else
            operationsHistory.reversed()
    }
}