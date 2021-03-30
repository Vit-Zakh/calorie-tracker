package com.example.calorietracker.dailyintake

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.models.ui.DailyIntakeProps

class IntakeItemDiffCallback : DiffUtil.ItemCallback<DailyIntakeProps>() {

    override fun areItemsTheSame(oldItem: DailyIntakeProps, newItem: DailyIntakeProps): Boolean {
        return when {
            oldItem is DailyIntakeProps.MealProps && newItem is DailyIntakeProps.MealProps -> {
                oldItem.id == newItem.id
            }
            oldItem is DailyIntakeProps.UserProps && newItem is DailyIntakeProps.UserProps -> {
                true
            }
            oldItem is DailyIntakeProps.TextLine && newItem is DailyIntakeProps.TextLine -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: DailyIntakeProps, newItem: DailyIntakeProps): Boolean {
        return when {
            oldItem is DailyIntakeProps.MealProps && newItem is DailyIntakeProps.MealProps -> {
                oldItem == newItem
            }
            oldItem is DailyIntakeProps.UserProps && newItem is DailyIntakeProps.UserProps -> {
                oldItem == newItem
            }
            oldItem is DailyIntakeProps.TextLine && newItem is DailyIntakeProps.TextLine -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    override fun getChangePayload(oldItem: DailyIntakeProps, newItem: DailyIntakeProps): Any? {
        return when {
            oldItem is DailyIntakeProps.UserProps && newItem is DailyIntakeProps.UserProps -> {
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
                        newItem.userWeight?.let { diff.putFloat("userName", it) }
                    }
                }
                diff
            }
            else -> return super.getChangePayload(oldItem, newItem)
        }
    }
}
