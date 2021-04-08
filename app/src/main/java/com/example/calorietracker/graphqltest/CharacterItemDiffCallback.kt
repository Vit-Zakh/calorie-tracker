package com.example.calorietracker.graphqltest

import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.graphqltest.models.CharacterModel

class CharacterItemDiffCallback : DiffUtil.ItemCallback<CharacterModel>() {

    override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
        return when {
            oldItem is CharacterModel && newItem is CharacterModel -> {
                oldItem.name == newItem.name
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
        return when {
            oldItem is CharacterModel && newItem is CharacterModel -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
