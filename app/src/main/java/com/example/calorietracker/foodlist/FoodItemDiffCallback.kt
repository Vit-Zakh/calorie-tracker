package com.example.calorietracker.foodlist

import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.RecyclerData

class FoodItemDiffCallback : DiffUtil.ItemCallback<RecyclerData>() {

    override fun areItemsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
        return when {
            oldItem is RecyclerData.Food && newItem is RecyclerData.Food -> {
                oldItem.id == newItem.id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
        return when {
            oldItem is RecyclerData.Food && newItem is RecyclerData.Food -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
