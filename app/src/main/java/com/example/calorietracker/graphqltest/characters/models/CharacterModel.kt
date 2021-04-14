package com.example.calorietracker.graphqltest.characters.models

import com.example.calorietracker.GetCharactersQuery

data class CharacterModel(
    val name: String,
    val image: String?,
    val status: String,
    val species: String,
)

fun GetCharactersQuery.Result.mapToBusinessModel(): CharacterModel {
    return CharacterModel(
        name = this.name ?: "",
        image = this.image,
        status = this.status ?: "",
        species = this.species ?: ""
    )
}

fun GetCharactersQuery.Characters.mapToBusinessModel(): List<CharacterModel> {
    val list = mutableListOf<CharacterModel>()
    this.results?.forEach {
        list.add(
            CharacterModel(
                name = it?.name.toString(),
                image = it?.image.toString(),
                status = it?.status.toString(),
                species = it?.species.toString(),
            )
        )
    }
    return list
}
