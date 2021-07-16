package dev.sourabh.madscalculator.android.utils

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import dev.sourabh.madscalculator.android.R

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("button_color")
        fun setButtonColor(view: MaterialButton, type: Int = Constants.CALCULATOR_BUTTON_TYPE_NUMERICAL) {
            val colorToSet = when(type){
                Constants.CALCULATOR_BUTTON_TYPE_NUMERICAL -> ContextCompat.getColor(view.context, R.color.calculator_button_color)
                Constants.CALCULATOR_BUTTON_TYPE_OPERATOR -> ContextCompat.getColor(view.context, R.color.calculator_operator_button_color)
                else -> ContextCompat.getColor(view.context, R.color.calculator_function_button_color)
            }
            view.setTextColor(colorToSet)
        }
    }
}