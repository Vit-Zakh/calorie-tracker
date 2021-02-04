package com.example.calorietracker

import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.calorietracker.databinding.DialogAddMealBinding
import com.example.calorietracker.extensions.loadImageByUrl
import com.example.calorietracker.foodlist.FoodListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

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
                Toast.makeText(
                    context,
                    "Meal added!",
                    Toast.LENGTH_SHORT
                ).show()
                model.addMealToList(
                    RecyclerData.Meal(
                        Random.nextInt(20, 1998),
                        "Popcorn",
                        "https://images.unsplash.com/photo-1578849278619-e73505e9610f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80",
                        490f,
                        213f
                    )
                )
                Log.d("MY_TAG", "initDialog: ${model.getMealListSize()}")
                dismiss()
            }
            it.cancelDialogAction.setOnClickListener {
                dismiss()
            }
        }
    }
}
