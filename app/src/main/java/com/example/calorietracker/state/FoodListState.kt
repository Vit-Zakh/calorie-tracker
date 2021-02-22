package com.example.calorietracker.state

import com.example.calorietracker.models.network.FoodListResponse
import com.example.calorietracker.models.network.FoodResponse
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.models.ui.mapToDomainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListState @Inject constructor() {
    private val _cashedFoodList = MutableStateFlow(listOf<FoodResponse>())

    val cashedFoodList: StateFlow<List<FoodResponse>> = _cashedFoodList

    fun refreshFoodList(food: FoodListResponse) {
        _cashedFoodList.value = food.food
    }

    fun addFood(food: FoodProps) {
        val updatedList = _cashedFoodList.value.toMutableList()
        updatedList.add(3, food.mapToDomainModel())
        _cashedFoodList.value = updatedList
    }

    fun deleteFood(index: Int) {
        val updatedList = _cashedFoodList.value.toMutableList()
        updatedList.removeAt(index)
        _cashedFoodList.value = updatedList
    }
}
