package com.example.calorietracker.state

import com.example.calorietracker.extensions.mapToDomainModel
import com.example.calorietracker.models.DomainModel
import com.example.calorietracker.models.UiModel
import com.example.calorietracker.network.MealsListResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsState @Inject constructor() {
    private val _cashedMealsList = MutableStateFlow(listOf<DomainModel.Meal>())

    val cashedMealsList: StateFlow<List<DomainModel.Meal>> = _cashedMealsList

    fun refreshMealsList(meals: MealsListResponse) {
        _cashedMealsList.value = meals.meals
    }

    fun addMeal(meal: UiModel.Meal) {
        val updatedList = _cashedMealsList.value.toMutableList()
        updatedList.add(meal.mapToDomainModel())
        _cashedMealsList.value = updatedList
    }

    fun deleteMeal(index: Int) {
        val updatedList = _cashedMealsList.value.toMutableList()
        updatedList.removeAt(index)
        _cashedMealsList.value = updatedList
    }
}
