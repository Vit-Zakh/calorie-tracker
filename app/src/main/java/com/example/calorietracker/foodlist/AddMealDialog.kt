package com.example.calorietracker.foodlist

import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.databinding.DialogAddMealBinding
import com.example.calorietracker.models.ui.mapToMeal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMealDialog : DialogFragment() {

    private var addMealBinding: DialogAddMealBinding? = null
    private val viewModel: FoodListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DialogAddMealBinding.inflate(inflater, container, false)
        addMealBinding = binding
        viewModel.foodListFragmentProps.observe(viewLifecycleOwner) { props ->
            binding.food = props.foodInDialog
            binding.addMealDialogAction.setOnClickListener {
                if (TextUtils.isEmpty(binding.mealWeightDialog.text)) {
                    Toast.makeText(context, "Dish size cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    props.addMealDialogAction(
                        props.foodInDialog.mapToMeal(
                            weight = binding.mealWeightDialog.text.toString().toFloat()
                        ), this
                    )
                }
            }
            binding.cancelDialogAction.setOnClickListener { props.dismissDialogAction(this) }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.let {
            val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
            it.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        }
    }
}
