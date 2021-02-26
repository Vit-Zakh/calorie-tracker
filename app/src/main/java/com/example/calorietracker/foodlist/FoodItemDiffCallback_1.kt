package com.example.calorietracker.foodlist

import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.models.ui.FoodListProps

class FoodItemDiffCallback_1 : DiffUtil.ItemCallback<FoodListProps>() {

    override fun areItemsTheSame(oldItem: FoodListProps, newItem: FoodListProps): Boolean {
        return when {
            oldItem is FoodListProps.FoodProps && newItem is FoodListProps.FoodProps -> {
                oldItem.id == newItem.id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: FoodListProps, newItem: FoodListProps): Boolean {
        return when {
            oldItem is FoodListProps.FoodProps && newItem is FoodListProps.FoodProps -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
