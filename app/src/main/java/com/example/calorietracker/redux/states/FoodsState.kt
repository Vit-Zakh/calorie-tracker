package com.example.calorietracker.redux.states

import com.example.calorietracker.models.network.FoodResponse
import com.example.calorietracker.redux.actions.*

data class FoodsState(
    val foodsList: List<FoodResponse> = listOf(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false
) {

    fun reduce(action: ReduxAction): FoodsState {
        return when (action) {
            is StartFetchingFoods -> this.copy(isLoading = true)
            is FailFetchingFood -> this.copy(isFailed = true, isLoading = false)
            is SucceedFetchingFood -> this.copy(
                foodsList = action.foodList,
                isLoading = false,
                isFailed = false
            )
            is FetchInitialData -> this.copy(isLoading = true, isFailed = false)
            is AddFood -> this.copy(foodsList = foodsList + action.food)
            is RemoveFood -> this.copy(
                foodsList = foodsList.toMutableList().apply { removeAt(action.index) }
            )
            else -> this
        }
    }
}
