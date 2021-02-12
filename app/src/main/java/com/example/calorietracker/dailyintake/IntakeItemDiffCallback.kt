package com.example.calorietracker.dailyintake

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.data.RecyclerData

class IntakeItemDiffCallback : DiffUtil.ItemCallback<RecyclerData>() {

    override fun areItemsTheSame(oldItem: RecyclerData, newItem: RecyclerData): Boolean {
        return when {
            oldItem is RecyclerData.Meal && newItem is RecyclerData.Meal -> {
                oldItem.id == newItem.id
            }
            oldItem is RecyclerData.User && newItem is RecyclerData.User -> {
                true
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

    override fun getChangePayload(oldItem: RecyclerData, newItem: RecyclerData): Any? {
        return when {
            oldItem is RecyclerData.User && newItem is RecyclerData.User -> {
                val diff = Bundle()
                when {
                    newItem.userIntake != oldItem.userIntake -> {
                        diff.putDouble("dailyIntake", newItem.userIntake)
                    }
                    newItem.userImage != oldItem.userImage -> {
                        diff.putString("userImage", newItem.userImage)
                    }
                    newItem.userName != oldItem.userName -> {
                        diff.putString("userName", newItem.userName)
                    }
                    newItem.userWeight != oldItem.userWeight -> {
                        diff.putFloat("userName", newItem.userWeight)
                    }
                }
                diff
            }
            else -> return super.getChangePayload(oldItem, newItem)
        }
    }
}
