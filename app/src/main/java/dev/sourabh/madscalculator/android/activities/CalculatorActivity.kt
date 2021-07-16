package dev.sourabh.madscalculator.android.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.GridLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import dev.sourabh.madscalculator.android.databinding.ActivityCalculatorBinding
import dev.sourabh.madscalculator.android.models.CalculatorButton
import dev.sourabh.madscalculator.android.viewmodels.CalculatorActivityViewModel

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val viewModel: CalculatorActivityViewModel by viewModels()
        initCalculatorButtonsRecyclerView(viewModel.getCalculatorButtons())
    }

    private fun initCalculatorButtonsRecyclerView(calculatorButtons: List<CalculatorButton>) {
        binding.rvButtons.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@CalculatorActivity, 4)
        }
    }
}