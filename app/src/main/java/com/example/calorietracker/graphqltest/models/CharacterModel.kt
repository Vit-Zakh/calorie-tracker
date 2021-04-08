package com.example.calorietracker.graphqltest.models

import com.example.calorietracker.GetCharactersQuery

data class CharacterModel(
    val name: String,
    val image: String?,
    val status: String,
    val species: String
)

fun GetCharactersQuery.Result.mapToBusinessModel(): CharacterModel {
    return CharacterModel(
        name = this.name ?: "unknown",
        image = this.image,
        status = this.status ?: "unknown",
        species = this.species ?: "unknown"
    )
}
