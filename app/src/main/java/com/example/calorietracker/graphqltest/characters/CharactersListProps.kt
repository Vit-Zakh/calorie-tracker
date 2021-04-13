package com.example.calorietracker.graphqltest.characters

sealed class CharactersListProps {

    class LoadedCharactersList(val charactersList: List<CharacterModel?>?) : CharactersListProps()
    object LoadingCharactersList : CharactersListProps()
    object EmptyCharactersList : CharactersListProps()
    object FailedCharactersList : CharactersListProps()
}
