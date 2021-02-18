package com.example.calorietracker.repositories

import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FoodListRepository {

    suspend fun fetchUser(): StateFlow<UserResponse>

    suspend fun fetchFood(): Flow<List<RecyclerData.Food>>

    suspend fun addFood(food: RecyclerData.Food)

    suspend fun deleteFood(index: Int)

    suspend fun addMealToList(meal: RecyclerData.Meal)

    suspend fun refreshFood()

    suspend fun refreshUser()
}
