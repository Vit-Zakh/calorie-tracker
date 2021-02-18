package com.example.calorietracker.repositories

import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.MealsListResponse
import com.example.calorietracker.network.UserResponse
import kotlinx.coroutines.flow.StateFlow

interface DailyIntakeRepository {

    val user: StateFlow<UserResponse>
    val meals: StateFlow<MealsListResponse>

    suspend fun addMeal(meal: RecyclerData.Meal)

    suspend fun removeMeal(index: Int)

    suspend fun refreshState()
}
