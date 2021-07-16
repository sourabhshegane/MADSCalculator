package dev.sourabh.madscalculator.android

import dev.sourabh.madscalculator.android.utils.Calculator
import org.junit.Assert
import org.junit.Test

class CalculatorTest {

    val calculator = Calculator()

    @Test
    fun checkAgainstEmptyExpression() {
        val result = calculator.calculate("")
        Assert.assertEquals(0, result)
    }
}