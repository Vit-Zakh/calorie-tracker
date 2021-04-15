package com.example.calorietracker.redux.actions

import com.example.calorietracker.models.network.FoodResponse

class SucceedFetchingFood(val foodList: List<FoodResponse>) : ReduxAction
