package com.example.calorietracker.graphqltest.characters

import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingCharacters(val data: GetCharactersQuery.Characters?) : ReduxAction
