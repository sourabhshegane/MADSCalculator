package dev.sourabh.madscalculator.android

import dev.sourabh.madscalculator.android.utils.Calculator
import org.junit.Assert
import org.junit.Test

class CalculatorTest {

    val calculator = Calculator()

    @Test
    fun checkSimpleExpression() {
        val actualResult = calculator.calculate("5+2")
        Assert.assertEquals(7, actualResult)
    }

    @Test
    fun checkAgainstExpressionWithOperatorAtLast() {
        val actualResult = calculator.calculate("5+2*")
        Assert.assertEquals(-1, actualResult)
    }

    @Test
    fun checkAgainstExpressionWithOperatorAtStart() {
        val actualResult = calculator.calculate("/5+2")
        Assert.assertEquals(-1, actualResult)
    }

    @Test
    fun checkAgainstExpressionWithOperatorsAtBothEnd() {
        val actualResult = calculator.calculate("/5+2-")
        Assert.assertEquals(-1, actualResult)
    }

    @Test
    fun checkAgainstInvalidExpression() {
        val actualResult = calculator.calculate("/5++2-")
        Assert.assertEquals(-1, actualResult)
    }

    @Test
    fun checkExpressionWithMADSequence() {
        val actualResult = calculator.calculate("50+20/10")
        Assert.assertEquals(7, actualResult)
    }

    @Test
    fun checkExpressionWithOnlyAdditionOperator() {
        val actualResult = calculator.calculate("50+20+10")
        Assert.assertEquals(80, actualResult)
    }

    @Test
    fun checkExpressionWithOnlySubtractOperator() {
        val actualResult = calculator.calculate("10-2-3")
        Assert.assertEquals(5, actualResult)
    }

    @Test
    fun checkExpressionWithOnlyMultiplicationOperator() {
        val actualResult = calculator.calculate("10*2*8")
        Assert.assertEquals(160, actualResult)
    }
}