package com.example.calorietracker.redux.states

import com.example.calorietracker.redux.actions.ReduxAction

data class AppState(
    val mealsListState: MealsState = MealsState(),
    val userState: UserState = UserState(),
    val foodsState: FoodsState = FoodsState(),
    val foodDialogState: FoodDialogState = FoodDialogState()
) {

    fun reduce(action: ReduxAction): AppState {
        return this.copy(
            mealsListState = mealsListState.reduce(action),
            userState = userState.reduce(action),
            foodsState = foodsState.reduce(action),
            foodDialogState = foodDialogState.reduce(action)
        )
    }
}
