package dev.sourabh.madscalculator.android.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dev.sourabh.madscalculator.android.R
import dev.sourabh.madscalculator.android.adapters.CalculatorButtonsRecyclerViewAdapter
import dev.sourabh.madscalculator.android.bottom_sheets.OperationsHistoryBottomSheet
import dev.sourabh.madscalculator.android.databinding.ActivityCalculatorBinding
import dev.sourabh.madscalculator.android.models.CalculatorButton
import dev.sourabh.madscalculator.android.models.Operation
import dev.sourabh.madscalculator.android.utils.MADSCalculator
import dev.sourabh.madscalculator.android.viewmodels.CalculatorActivityViewModel


class CalculatorActivity : AppCompatActivity(),
    CalculatorButtonsRecyclerViewAdapter.OnCalculatorButtonClicked,
    OperationsHistoryBottomSheet.OnExpressionFromHistoryRequested {

    private lateinit var historyBottomSheet: OperationsHistoryBottomSheet
    private val INVALID_EXPRESSION = -1
    private lateinit var viewModel: CalculatorActivityViewModel
    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MADSCalculator)
        binding = ActivityCalculatorBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(CalculatorActivityViewModel::class.java)
        initCalculatorButtonsRecyclerView(viewModel.getCalculatorButtons())
        initUI()
    }

    private fun initUI() {
        with(binding) {
            edInput.apply {
                showSoftInputOnFocus = false
                requestFocus()
            }

            ivBackspace.setOnClickListener {
                backSpace()
            }

            btnClear.setOnClickListener {
                clearInput()
            }

            ivHistory.setOnClickListener {
                showHistory()
            }
        }
    }

    private fun showHistory() {
        val history = viewModel.getOperationsHistory()
        if (history.isNotEmpty()) {
            historyBottomSheet = OperationsHistoryBottomSheet(
                history,
                this@CalculatorActivity
            )

            historyBottomSheet.show(supportFragmentManager, "")
        }
    }

    private fun initCalculatorButtonsRecyclerView(calculatorButtons: List<CalculatorButton>) {
        binding.rvButtons.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@CalculatorActivity, 4)
            adapter = CalculatorButtonsRecyclerViewAdapter(
                calculatorButtons,
                this@CalculatorActivity
            )
        }
    }

    override fun onCalculatorButtonClicked(calculatorButton: CalculatorButton) {
        handleButtonClick(calculatorButton)
    }

    private fun handleButtonClick(calculatorButton: CalculatorButton) {
        when (calculatorButton.text) {
            getString(R.string.equals_sign) -> calculateAnswer()
            getString(R.string.ans) -> setPreviousResult()
            else -> binding.edInput.append(calculatorButton.text)
        }
    }

    private fun calculateAnswer() {
        val expression = binding.edInput.text.toString().trim()
        val calculator = MADSCalculator()
        val result = calculator.calculate(expression)
        if (result == INVALID_EXPRESSION) {
            Toast.makeText(
                this@CalculatorActivity,
                "Please enter a valid expression",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.addOperationToOperationHistory(expression, result)
            binding.tvResult.text = "" + result
        }
    }

    private fun backSpace() {
        val currentInput = binding.edInput.text.toString()
        val inputAfterBackspace = currentInput.dropLast(1)
        binding.edInput.setText(inputAfterBackspace)
        binding.edInput.setSelection(binding.edInput.text.toString().length)
    }

    private fun clearInput() {
        binding.edInput.text.clear()
        binding.tvResult.text = ""
    }

    private fun setPreviousResult() {
        val previousResult = viewModel.getPreviousResult()
        if (previousResult != INVALID_EXPRESSION) {
            binding.edInput.append("" + previousResult)
        }
    }

    override fun onExpressionFromHistoryRequested(requestedOperation: Operation) {
        with(binding){
            edInput.setText(requestedOperation.expression)
            tvResult.text = "" + requestedOperation.result
        }
        closeHistoryBottomSheet()
    }

    private fun closeHistoryBottomSheet() {
        if(this::historyBottomSheet.isInitialized){
            historyBottomSheet.dismiss()
        }
    }
}