package com.example.calorietracker.redux.states

import android.util.Log
import com.example.calorietracker.redux.actions.ReduxAction

data class AppState(
    val mealsListState: MealsState = MealsState(),
    val userState: UserState = UserState(),
    val foodsState: FoodsState = FoodsState(),
) {

    fun reduce(action: ReduxAction): AppState {
        Log.d("test_TAG", "reduce is called. meals state: $mealsListState, user state: $userState, action: $action")
        return this.copy(
            mealsListState = mealsListState.reduce(action),
            userState = userState.reduce(action),
            foodsState = foodsState.reduce(action)
        )
    }
}
