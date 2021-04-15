package com.example.calorietracker.redux.states

import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.redux.actions.OpenFoodDialog
import com.example.calorietracker.redux.actions.ReduxAction

data class FoodDialogState(
    val food: FoodProps = FoodProps("", "", "", 0f),
) {
    fun reduce(action: ReduxAction): FoodDialogState {
        return when (action) {
            is OpenFoodDialog -> this.copy(
                food = action.food,
            )
            else -> this
        }
    }
}
