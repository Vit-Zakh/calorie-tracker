package com.example.calorietracker.repositories

import com.example.calorietracker.data.RecyclerData
import com.example.calorietracker.network.MealsListResponse
import com.example.calorietracker.network.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow

interface DailyIntakeRepository {

    val user: MutableStateFlow<UserResponse>
    val meals: MutableStateFlow<MealsListResponse>

    suspend fun addMeal(meal: RecyclerData.Meal)

    suspend fun removeMeal(index: Int)

    suspend fun refreshState()
}
