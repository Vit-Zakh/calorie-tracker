package com.example.calorietracker.foodlist

import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.models.UiModel

class FoodItemDiffCallback : DiffUtil.ItemCallback<UiModel>() {

    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return when {
            oldItem is UiModel.Food && newItem is UiModel.Food -> {
                oldItem.id == newItem.id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return when {
            oldItem is UiModel.Food && newItem is UiModel.Food -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
