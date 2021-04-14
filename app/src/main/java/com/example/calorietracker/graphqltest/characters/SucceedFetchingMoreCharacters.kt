package com.example.calorietracker.graphqltest.characters

import com.example.calorietracker.graphqltest.characters.models.CharacterModel
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingMoreCharacters(val data: List<CharacterModel>, val next: Int?) : ReduxAction
