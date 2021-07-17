package dev.sourabh.madscalculator.android.utils

import java.lang.Exception
import java.util.*

class MADSCalculator {

    var operatorStack = ArrayDeque<Char>()
    var operandStack = ArrayDeque<Int>()

    private fun isCurrentTopHavingGreaterPriority(characterToCheckWith: Char): Boolean {
        val currentTopPriority = getPriority(operatorStack.peek())
        val currentCharacterPriority = getPriority(characterToCheckWith)
        return currentTopPriority >= currentCharacterPriority
    }

    private fun getPriority(character: Char): Int {
        return when (character) {
            '*' -> 4
            '+' -> 3
            '/' -> 2
            else -> 1
        }
    }

    private fun performOperation(topOperand: Int, bottomOperand: Int, operator: Char): Int {
        return when (operator) {
            '+' -> (bottomOperand + topOperand)
            '*' -> (bottomOperand * topOperand)
            '-' -> (bottomOperand - topOperand)
            else -> (bottomOperand / topOperand)
        }
    }

    private fun isCurrentCharacterAnOperator(characterToCheck: Char): Boolean {
        val operators = listOf('+', '-', '*', '/')
        return operators.contains(characterToCheck)
    }

    fun calculate(exp: String): Int {
        try {
            if(isExpressionValid(exp)){
                val numericalString = StringBuilder()
                for (i in 0 until exp.length) {
                    val currentCharacter = exp[i]
                    if (isCurrentCharacterAnOperator(currentCharacter)) {
                        val numericalStringAsInt = Integer.parseInt(numericalString.toString())
                        operandStack.push(numericalStringAsInt)
                        numericalString.delete(0, numericalString.length)

                        if (operatorStack.isEmpty()) {
                            operatorStack.push(currentCharacter)
                        } else {
                            if (isCurrentTopHavingGreaterPriority(currentCharacter)) {
                                val result = getResult()
                                operandStack.push(result)
                                operatorStack.push(currentCharacter)

                            } else {
                                operatorStack.push(currentCharacter)
                            }
                        }
                    } else {
                        numericalString.append(currentCharacter)
                    }
                }

                val numericalStringAsInt = Integer.parseInt(numericalString.toString())
                operandStack.push(numericalStringAsInt)

                var i = 0
                val size = operatorStack.size
                while (i < size) {
                    i += 1
                    val result = getResult()
                    operandStack.push(result)
                }

                return operandStack.pop()
            }else{
                return -1
            }
        }catch (exception: Exception){
            return -1
        }
    }

    private fun getResult(): Int {
        val topOperand = operandStack.pop()
        val bottomOperand = operandStack.pop()
        val operator = operatorStack.pop()
        val result = performOperation(topOperand, bottomOperand, operator)
        return result
    }

    private fun isExpressionValid(expressionToCheck: String): Boolean{
        if(expressionToCheck.endsWith("+") || expressionToCheck.endsWith("-") || expressionToCheck.endsWith("*") || expressionToCheck.endsWith("/")){
            return false
        }

        if (expressionToCheck.startsWith("+") || expressionToCheck.startsWith("-") || expressionToCheck.startsWith("*") || expressionToCheck.startsWith("/")){
            return false
        }

        return true
    }
}