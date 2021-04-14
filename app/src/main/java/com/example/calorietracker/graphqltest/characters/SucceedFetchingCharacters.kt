package com.example.calorietracker.graphqltest.characters

import com.example.calorietracker.graphqltest.characters.models.CharacterModel
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingCharacters(val data: List<CharacterModel>, val pages: Int?, val next: Int?) : ReduxAction
