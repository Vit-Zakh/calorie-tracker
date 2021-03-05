package com.example.calorietracker.foodlist

import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.models.ui.FoodProps

class FoodItemDiffCallback : DiffUtil.ItemCallback<FoodProps>() {

    override fun areItemsTheSame(oldItem: FoodProps, newItem: FoodProps): Boolean {
        return when {
            oldItem is FoodProps && newItem is FoodProps -> {
                oldItem.id == newItem.id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: FoodProps, newItem: FoodProps): Boolean {
        return when {
            oldItem is FoodProps && newItem is FoodProps -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
