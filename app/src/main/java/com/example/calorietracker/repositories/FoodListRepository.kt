package com.example.calorietracker.repositories

import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.FoodListResponse
import com.example.calorietracker.network.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow

interface FoodListRepository {

    val user: MutableStateFlow<UserResponse>
    val food: MutableStateFlow<FoodListResponse>

    suspend fun addFood(food: RecyclerData.Food)

    suspend fun deleteFood(index: Int)

    suspend fun addMealToList(meal: RecyclerData.Meal)

    suspend fun refreshFood()

    suspend fun refreshUser()
}
