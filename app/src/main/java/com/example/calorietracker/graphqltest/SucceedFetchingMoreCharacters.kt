package com.example.calorietracker.graphqltest

import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingMoreCharacters(val data: GetCharactersQuery.Characters) : ReduxAction
