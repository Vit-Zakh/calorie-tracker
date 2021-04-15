package com.example.calorietracker.redux.actions

import com.example.calorietracker.models.network.MealResponse

class SucceedFetchingMeals(val meals: List<MealResponse>) : ReduxAction
