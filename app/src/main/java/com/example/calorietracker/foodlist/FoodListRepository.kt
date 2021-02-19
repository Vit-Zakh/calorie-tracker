package com.example.calorietracker.foodlist

import com.example.calorietracker.models.DomainModel
import com.example.calorietracker.models.UiModel
import com.example.calorietracker.network.UserResponse
import kotlinx.coroutines.flow.StateFlow

interface FoodListRepository {

    val user: StateFlow<UserResponse>
    val food: StateFlow<List<DomainModel.Food>>

    fun addFood(food: UiModel.Food)

    fun deleteFood(index: Int)

    fun addMealToList(meal: UiModel.Meal)

    suspend fun refreshFood()

    suspend fun refreshUser()
}
