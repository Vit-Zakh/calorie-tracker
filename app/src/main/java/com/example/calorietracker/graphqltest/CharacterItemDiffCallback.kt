package com.example.calorietracker.graphqltest

import androidx.recyclerview.widget.DiffUtil
import com.example.calorietracker.GetCharactersQuery

class CharacterItemDiffCallback : DiffUtil.ItemCallback<GetCharactersQuery.Result>() {

    override fun areItemsTheSame(oldItem: GetCharactersQuery.Result, newItem: GetCharactersQuery.Result): Boolean {
        return when {
            oldItem is GetCharactersQuery.Result && newItem is GetCharactersQuery.Result -> {
                oldItem.name == newItem.name
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: GetCharactersQuery.Result, newItem: GetCharactersQuery.Result): Boolean {
        return when {
            oldItem is GetCharactersQuery.Result && newItem is GetCharactersQuery.Result -> {
                oldItem == newItem
            }
            else -> false
        }
    }
}
