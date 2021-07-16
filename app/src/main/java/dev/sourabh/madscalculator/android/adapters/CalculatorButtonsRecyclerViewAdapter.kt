package dev.sourabh.madscalculator.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sourabh.madscalculator.android.databinding.CalculatorButtonBinding
import dev.sourabh.madscalculator.android.models.CalculatorButton

class CalculatorButtonsRecyclerViewAdapter(
    private val buttons: List<CalculatorButton>,
    private val onCalculatorButtonClicked: OnCalculatorButtonClicked
) : RecyclerView.Adapter<CalculatorButtonsRecyclerViewAdapter.CalculatorButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorButtonViewHolder {
        val layoutCalculatorButtonBinding =
            CalculatorButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalculatorButtonViewHolder(layoutCalculatorButtonBinding)
    }

    override fun onBindViewHolder(holder: CalculatorButtonViewHolder, position: Int) {
        val currentCalculatorButton = buttons[position]
        holder.bind(currentCalculatorButton)
    }

    override fun getItemCount(): Int {
        return buttons.size
    }

    inner class CalculatorButtonViewHolder(
        private val layoutCalculatorButtonBinding: CalculatorButtonBinding
    ) : RecyclerView.ViewHolder(layoutCalculatorButtonBinding.root) {

        fun bind(calculatorButton: CalculatorButton) {
            layoutCalculatorButtonBinding.calculatorButton = calculatorButton
            layoutCalculatorButtonBinding.root.setOnClickListener {
                onCalculatorButtonClicked.onCalculatorButtonClicked(calculatorButton)
            }
            layoutCalculatorButtonBinding.executePendingBindings()
        }
    }

    interface OnCalculatorButtonClicked {
        fun onCalculatorButtonClicked(calculatorButton: CalculatorButton)
    }
}