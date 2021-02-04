package com.example.calorietracker

import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.calorietracker.databinding.DialogAddMealBinding
import com.example.calorietracker.extensions.loadImageByUrl
import com.example.calorietracker.foodlist.FoodListViewModel
import com.example.calorietracker.utils.MealMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_add_meal.*

@AndroidEntryPoint
class AddMealDialog : DialogFragment() {

    private var addMealBinding: DialogAddMealBinding? = null
    private val args: AddMealDialogArgs by navArgs()
    private val model: FoodListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogAddMealBinding.inflate(inflater, container, false)
        addMealBinding = binding
        initDialog(args.Food)
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

    private fun initDialog(food: RecyclerData.Food) {
        addMealBinding?.let {
            it.foodImageDialog.loadImageByUrl(food.imageUrl)
            it.mealNameTextDialog.text = food.name
            it.addMealDialogAction.setOnClickListener {
                addMealToList(
                    food = food,
                    weight = this.sizeEditTextDialog.text.toString().toFloat()
                )
                dismiss()
            }
            it.cancelDialogAction.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun addMealToList(food: RecyclerData.Food, weight: Float) {
        val meal = MealMapper.mapToMeal(food, weight)
        model.addMealToList(meal)
    }
}
