package com.example.calorietracker.redux.states

import com.example.calorietracker.models.network.MealResponse
import com.example.calorietracker.redux.actions.*

data class MealsState(
    val mealsList: List<MealResponse> = listOf(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false
) {

    fun reduce(action: ReduxAction): MealsState {
        return when (action) {
            is StartFetchingMeals -> this.copy(isLoading = true)
            is FailFetchingMeals -> this.copy(isFailed = true, isLoading = false)
            is SucceedFetchingMeals -> this.copy(
                mealsList = action.meals,
                isLoading = false,
                isFailed = false
            )
            is FetchInitialData -> this.copy(isLoading = true, isFailed = false)
            is AddMeal -> this.copy(mealsList = mealsList + action.meal, isFailed = false, isLoading = false)
            is RemoveMeal -> this.copy(
                mealsList = mealsList.toMutableList().apply { removeAt(action.index) }
            )
            else -> this
        }
    }
}
