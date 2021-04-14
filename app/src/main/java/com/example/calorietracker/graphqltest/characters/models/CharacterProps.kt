package com.example.calorietracker.graphqltest.characters.models

data class CharacterProps(
    val name: String,
    val image: String?,
    val status: String,
    val species: String
)

fun CharacterModel.mapToUiModel(): CharacterProps {
    return CharacterProps(
        name = this.name,
        image = this.image,
        status = this.status,
        species = this.species
    )
}
