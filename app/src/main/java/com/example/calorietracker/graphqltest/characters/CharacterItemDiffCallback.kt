package com.example.calorietracker.graphqltest.characters

import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.graphqltest.characters.models.CharacterProps

class CharacterItemDiffCallback : DiffUtil.ItemCallback<CharacterProps>() {

    override fun areItemsTheSame(oldItem: CharacterProps, newItem: CharacterProps): Boolean {
        return when {
            oldItem is CharacterProps && newItem is CharacterProps -> {
                oldItem.name == newItem.name
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: CharacterProps, newItem: CharacterProps): Boolean {
        return when {
            oldItem is CharacterProps && newItem is CharacterProps -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
