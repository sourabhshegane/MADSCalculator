package dev.sourabh.madscalculator.android.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.sourabh.madscalculator.android.adapters.OperationsHistoryRecyclerViewAdapter
import dev.sourabh.madscalculator.android.databinding.OperationsHistoryBottomSheetBinding
import dev.sourabh.madscalculator.android.models.Operation


class OperationsHistoryBottomSheet(
    private val operations: List<Operation>,
    private val onExpressionFromHistoryRequested: OnExpressionFromHistoryRequested
) : BottomSheetDialogFragment(),
    OperationsHistoryRecyclerViewAdapter.OnExpressionRequestedFromHistory {

    private lateinit var binding: OperationsHistoryBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OperationsHistoryBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            tvOperationsCount.text = "Your previous ${operations.size} operations"

            rvOperations.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )

                adapter = OperationsHistoryRecyclerViewAdapter(
                    operations,
                    this@OperationsHistoryBottomSheet
                )
            }
        }
    }

    interface OnExpressionFromHistoryRequested {
        fun onExpressionFromHistoryRequested(requestedOperation: Operation)
    }

    override fun onExpressionRequestedFromHistory(requestedOperation: Operation) {
        onExpressionFromHistoryRequested.onExpressionFromHistoryRequested(requestedOperation)
    }
}