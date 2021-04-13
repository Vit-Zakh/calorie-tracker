package com.example.calorietracker.redux.states

import com.example.calorietracker.redux.actions.ReduxAction

data class AppState(
    val mealsListState: MealsState = MealsState(),
    val userState: UserState = UserState(),
    val foodsState: FoodsState = FoodsState(),
    val foodDialogState: FoodDialogState = FoodDialogState(),
    val charactersState: CharactersState = CharactersState(),
    val locationsState: LocationsState = LocationsState(),
    val locationsWithTypeState: LocationsWithTypeState = LocationsWithTypeState(),
    val locationsWithCreatedStateState: LocationsWithCreatedState = LocationsWithCreatedState()
) {

    fun reduce(action: ReduxAction): AppState {
        return this.copy(
            mealsListState = mealsListState.reduce(action),
            userState = userState.reduce(action),
            foodsState = foodsState.reduce(action),
            foodDialogState = foodDialogState.reduce(action),
            charactersState = charactersState.reduce(action),
            locationsState = locationsState.reduce(action),
            locationsWithTypeState = locationsWithTypeState.reduce(action),
            locationsWithCreatedStateState = locationsWithCreatedStateState.reduce(action)
        )
    }
}
