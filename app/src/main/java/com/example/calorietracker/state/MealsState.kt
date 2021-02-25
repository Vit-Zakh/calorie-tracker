package com.example.calorietracker.state

import com.example.calorietracker.models.network.MealResponse
import com.example.calorietracker.models.network.MealsListResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.mapToDomainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsState @Inject constructor() {
    private val _cashedMealsList = MutableStateFlow(
        listOf<MealResponse>()
    )

    val cashedMealsList: StateFlow<List<MealResponse>> = _cashedMealsList

    fun refreshMealsList(meals: MealsListResponse) {
        // state -> Loading
        // if response success -> state success -> new value
        _cashedMealsList.value = meals.meals
        // if response error -> logd error
    }

    fun addMeal(mealProps: DailyIntakeProps.MealProps) {
        val updatedList = _cashedMealsList.value.toMutableList()
        updatedList.add(0, mealProps.mapToDomainModel())
        _cashedMealsList.value = updatedList
    }

    fun deleteMeal(index: Int) {
        val updatedList = _cashedMealsList.value.toMutableList()
        updatedList.removeAt(index)
        _cashedMealsList.value = updatedList
    }
}
