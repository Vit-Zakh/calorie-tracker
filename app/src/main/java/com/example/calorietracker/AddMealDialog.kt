package com.example.calorietracker

import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.calorietracker.databinding.DialogAddMealBinding
import com.example.calorietracker.extensions.loadImageByUrl

class AddMealDialog : DialogFragment() {

    private var addMealBinding: DialogAddMealBinding? = null
    private val args: AddMealDialogArgs by navArgs()

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
            }
            it.cancelDialogAction.setOnClickListener {
                dismiss()
            }
        }
    }
}
