package com.example.calorietracker.state

import android.util.Log
import com.example.calorietracker.extensions.mapToDomainModel
import com.example.calorietracker.models.DomainModel
import com.example.calorietracker.models.UiModel
import com.example.calorietracker.network.FoodListResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListState @Inject constructor() {
    private val _cashedFoodList = MutableStateFlow(listOf<DomainModel.Food>())

    val cashedFoodList: StateFlow<List<DomainModel.Food>> = _cashedFoodList

    fun refreshFoodList(food: FoodListResponse) {
        _cashedFoodList.value = food.food
    }

    fun addFood(food: UiModel.Food) {
        val updatedList = _cashedFoodList.value.toMutableList()
        updatedList.add(food.mapToDomainModel())
        _cashedFoodList.value = updatedList
    }

    fun deleteFood(index: Int) {
        val updatedList = _cashedFoodList.value.toMutableList()
        updatedList.removeAt(index)
        _cashedFoodList.value = updatedList
    }
}
