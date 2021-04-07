package com.example.calorietracker.graphqltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.R
import com.example.calorietracker.databinding.LayoutCharacterItemBinding
import com.example.calorietracker.utils.loadImageByUrl

class GraphListAdapter() :
    ListAdapter<GetCharactersQuery.Result, GraphListAdapter.CharacterViewHolder>(
        CharacterItemDiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        return try {
            CharacterViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_character_item, parent, false)
            )
        } catch (e: Exception) {
            throw RuntimeException("Crash while defining view holder")
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position) as GetCharactersQuery.Result)
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val characterBinding = LayoutCharacterItemBinding.bind(itemView)

        fun bind(character: GetCharactersQuery.Result) {
            characterBinding.characterName.text = character.name
            characterBinding.characterStatus.text = character.status
            characterBinding.characterImage.loadImageByUrl(character.image)
        }
    }
}
