package com.example.calorietracker.graphqltest.characters

import androidx.recyclerview.widget.DiffUtil

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
