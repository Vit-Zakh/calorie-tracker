package com.example.calorietracker.state

import com.example.calorietracker.models.network.MealResponse
import com.example.calorietracker.models.network.MealsListResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.mapToDomainModel
import com.example.calorietracker.network.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Error
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsState @Inject constructor() {

    data class FetchedMealsState(
        val mealsList: List<MealResponse> = listOf(),
        val isLoading: Boolean = false,
        val isFailed: Boolean = false
    )

    private val _cashedMealsList = MutableStateFlow(
        FetchedMealsState()
    )

    val cashedMealsList: StateFlow<FetchedMealsState> = _cashedMealsList

    fun startFetching() {
        _cashedMealsList.value = _cashedMealsList.value.copy(isLoading = true)
    }

    fun refreshMealsList(meals: NetworkResponse<MealsListResponse, Error>) {

        when (meals) {
            is NetworkResponse.Success -> _cashedMealsList.value = _cashedMealsList.value.copy(mealsList = meals.body.meals, isLoading = false)
            else -> {
                _cashedMealsList.value = _cashedMealsList.value.copy(isLoading = false, isFailed = true)
            }
        }
    }

    fun addMeal(meal: DailyIntakeProps.MealProps) {
        val updatedList = _cashedMealsList.value.mealsList.toMutableList()
        updatedList.add(0, meal.mapToDomainModel())
        _cashedMealsList.value = _cashedMealsList.value.copy(mealsList = updatedList, isLoading = false, isFailed = false)
    }

    fun deleteMeal(index: Int) {
        val updatedList = _cashedMealsList.value.mealsList.toMutableList()
        updatedList.removeAt(index)
        _cashedMealsList.value = _cashedMealsList.value.copy(mealsList = updatedList, isLoading = false, isFailed = false)
    }
}
