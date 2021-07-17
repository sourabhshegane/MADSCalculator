package dev.sourabh.madscalculator.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sourabh.madscalculator.android.databinding.OperationHistoryCardBinding
import dev.sourabh.madscalculator.android.models.Operation

class OperationsHistoryRecyclerViewAdapter(
    private val operations: List<Operation>,
    private val onExpressionRequestedFromHistory: OnExpressionRequestedFromHistory
) : RecyclerView.Adapter<OperationsHistoryRecyclerViewAdapter.OperationHistoryCardViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OperationHistoryCardViewHolder {
        val operationHistoryCardBinding =
            OperationHistoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OperationHistoryCardViewHolder(operationHistoryCardBinding)
    }

    override fun onBindViewHolder(holder: OperationHistoryCardViewHolder, position: Int) {
        val operation = operations[position]
        holder.bind(operation)
    }

    override fun getItemCount(): Int {
        return operations.size
    }

    inner class OperationHistoryCardViewHolder(private val operationHistoryCardBinding: OperationHistoryCardBinding) :
        RecyclerView.ViewHolder(operationHistoryCardBinding.root) {

        fun bind(operation: Operation) {
            operationHistoryCardBinding.operation = operation
            operationHistoryCardBinding.root.setOnClickListener {
                onExpressionRequestedFromHistory.onExpressionRequestedFromHistory(operation)
            }
            operationHistoryCardBinding.executePendingBindings()
        }
    }

    interface OnExpressionRequestedFromHistory {
        fun onExpressionRequestedFromHistory(requestedOperation: Operation)
    }
}