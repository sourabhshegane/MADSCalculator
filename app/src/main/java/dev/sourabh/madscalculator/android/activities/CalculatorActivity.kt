package dev.sourabh.madscalculator.android.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dev.sourabh.madscalculator.android.adapters.CalculatorButtonsRecyclerViewAdapter
import dev.sourabh.madscalculator.android.bottom_sheets.OperationsHistoryBottomSheet
import dev.sourabh.madscalculator.android.databinding.ActivityCalculatorBinding
import dev.sourabh.madscalculator.android.models.CalculatorButton
import dev.sourabh.madscalculator.android.utils.MADSCalculator
import dev.sourabh.madscalculator.android.viewmodels.CalculatorActivityViewModel

class CalculatorActivity : AppCompatActivity(),
    CalculatorButtonsRecyclerViewAdapter.OnCalculatorButtonClicked {

    private lateinit var viewModel: CalculatorActivityViewModel
    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            val historyBottomSheet = OperationsHistoryBottomSheet(
                history
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
            "=" -> calculateAnswer()
            "ANS" -> setPreviousResult()
            else -> binding.edInput.append(calculatorButton.text)
        }
    }

    private fun calculateAnswer() {
        val expression = binding.edInput.text.toString().trim()
        val calculator = MADSCalculator()
        val result = calculator.calculate(expression)
        if (result == -1) {
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
        if (previousResult != -1) {
            binding.edInput.append("" + previousResult)
        }
    }
}