package com.example.calorietracker.graphqltest

import com.example.calorietracker.GetCharactersQuery
import com.example.calorietracker.redux.actions.ReduxAction

class SucceedFetchingCharacters(val list: GetCharactersQuery.Characters) : ReduxAction
