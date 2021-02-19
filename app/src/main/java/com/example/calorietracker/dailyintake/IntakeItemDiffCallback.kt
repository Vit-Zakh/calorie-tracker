package com.example.calorietracker.dailyintake

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.models.UiModel

class IntakeItemDiffCallback : DiffUtil.ItemCallback<UiModel>() {

    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return when {
            oldItem is UiModel.Meal && newItem is UiModel.Meal -> {
                oldItem.id == newItem.id
            }
            oldItem is UiModel.User && newItem is UiModel.User -> {
                true
            }
            oldItem is UiModel.TextLine && newItem is UiModel.TextLine -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return when {
            oldItem is UiModel.Meal && newItem is UiModel.Meal -> {
                oldItem == newItem
            }
            oldItem is UiModel.User && newItem is UiModel.User -> {
                oldItem == newItem
            }
            oldItem is UiModel.TextLine && newItem is UiModel.TextLine -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    override fun getChangePayload(oldItem: UiModel, newItem: UiModel): Any? {
        return when {
            oldItem is UiModel.User && newItem is UiModel.User -> {
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
