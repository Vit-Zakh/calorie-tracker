package com.example.calorietracker.foodlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.databinding.LayoutFoodGridItemBinding
import com.example.calorietracker.models.ui.DailyIntakeProps.*
import com.example.calorietracker.models.ui.FoodProps
import java.lang.RuntimeException

class FoodListAdapter(private val clickListener: (food: FoodProps) -> Unit) :
    ListAdapter<FoodProps, FoodListAdapter.FoodViewHolder>(FoodItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return try {
            FoodViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_food_grid_item, parent, false)
            )
        } catch (e: Exception) {
            throw RuntimeException("Crash while defining view holder")
        }
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position) as FoodProps, clickListener)
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val foodBinding = LayoutFoodGridItemBinding.bind(itemView)

        fun bind(food: FoodProps, clickListener: (food: FoodProps) -> Unit) {
            foodBinding.food = food
            foodBinding.foodContainer.setOnClickListener { clickListener(food) }
        }
    }
}
