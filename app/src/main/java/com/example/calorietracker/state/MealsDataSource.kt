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
class MealsDataSource @Inject constructor() {

    data class MealsState(
        val mealsList: List<MealResponse> = listOf(),
        val isLoading: Boolean = false,
        val isFailed: Boolean = false
    )

    private val _mealsListFlow = MutableStateFlow(
        MealsState()
    )

    val mealsListFlow: StateFlow<MealsState> = _mealsListFlow

    fun setLoadingState() {
        _mealsListFlow.value = _mealsListFlow.value.copy(isLoading = true)
    }

    fun refreshMealsList(meals: NetworkResponse<MealsListResponse, Error>) {

        when (meals) {
            is NetworkResponse.Success -> _mealsListFlow.value = _mealsListFlow.value.copy(mealsList = meals.body.meals, isLoading = false)
            else -> {
                _mealsListFlow.value = _mealsListFlow.value.copy(isLoading = false, isFailed = true)
            }
        }
    }

    fun addMeal(meal: DailyIntakeProps.MealProps) {
        val updatedList = _mealsListFlow.value.mealsList.toMutableList()
        updatedList.add(0, meal.mapToDomainModel())
        _mealsListFlow.value = _mealsListFlow.value.copy(mealsList = updatedList, isLoading = false, isFailed = false)
    }

    fun deleteMeal(index: Int) {
        val updatedList = _mealsListFlow.value.mealsList.toMutableList()
        updatedList.removeAt(index)
        _mealsListFlow.value = _mealsListFlow.value.copy(mealsList = updatedList, isLoading = false, isFailed = false)
    }
}
