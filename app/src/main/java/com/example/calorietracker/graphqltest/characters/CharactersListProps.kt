package com.example.calorietracker.graphqltest.characters

import com.example.calorietracker.graphqltest.characters.models.CharacterProps

sealed class CharactersListProps {

    class LoadedCharactersList(val charactersList: List<CharacterProps>) : CharactersListProps()
    object LoadingCharactersList : CharactersListProps()
    object EmptyCharactersList : CharactersListProps()
    object FailedCharactersList : CharactersListProps()
}
