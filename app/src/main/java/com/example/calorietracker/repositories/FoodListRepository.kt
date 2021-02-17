package com.example.calorietracker.repositories

import com.example.calorietracker.data.RecyclerData
import kotlinx.coroutines.flow.Flow

interface FoodListRepository {

    suspend fun fetchUser(): Flow<RecyclerData.User>

    suspend fun fetchFood(): Flow<List<RecyclerData.Food>>

    suspend fun addFood(food: RecyclerData.Food)

    suspend fun deleteFood(index: Int)

    suspend fun addMealToList(meal: RecyclerData.Meal)

    suspend fun refreshFood()

    suspend fun refreshUser()
}
