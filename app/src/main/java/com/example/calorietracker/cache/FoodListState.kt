package com.example.calorietracker.cache

import com.example.calorietracker.network.FoodListResponse
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListState @Inject constructor() {
    private val _cashedFoodList = MutableStateFlow(FoodListResponse())

    val cashedFoodList: MutableStateFlow<FoodListResponse> = _cashedFoodList

    fun refreshFoodList(food: FoodListResponse) {
        _cashedFoodList.value = food
    }
}
