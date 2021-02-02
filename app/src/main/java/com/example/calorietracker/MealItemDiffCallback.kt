package com.example.calorietracker

import androidx.recyclerview.widget.DiffUtil

class MealItemDiffCallback : DiffUtil.ItemCallback<RecyclerData>() {

    override fun areItemsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
        return when {
            oldItem is RecyclerData.Meal && newItem is RecyclerData.Meal -> {
                oldItem.id == newItem.id
            }
            oldItem is RecyclerData.User && newItem is RecyclerData.User -> {
                oldItem.userIntake == newItem.userIntake
            }
            oldItem is RecyclerData.TextLine && newItem is RecyclerData.TextLine -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
        return when {
            oldItem is RecyclerData.Meal && newItem is RecyclerData.Meal -> {
                oldItem == newItem
            }
            oldItem is RecyclerData.User && newItem is RecyclerData.User -> {
                oldItem == newItem
            }
            oldItem is RecyclerData.TextLine && newItem is RecyclerData.TextLine -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
