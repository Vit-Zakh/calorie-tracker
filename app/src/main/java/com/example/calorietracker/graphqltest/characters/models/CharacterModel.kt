package com.example.calorietracker.graphqltest.characters.models

import com.example.calorietracker.GetCharactersQuery

data class CharacterModel(
    val name: String,
    val image: String?,
    val status: String,
    val species: String,
)

fun GetCharactersQuery.Characters?.mapToBusinessModel() = this?.results?.map {
    CharacterModel(
        name = it?.name.toString(),
        image = it?.image.toString(),
        status = it?.status.toString(),
        species = it?.species.toString(),
    )
} ?: listOf()
