package com.example.calorietracker.repositories

import com.example.calorietracker.data.RecyclerData
import kotlinx.coroutines.flow.Flow

interface DailyIntakeRepository {

    suspend fun fetchData(): Flow<List<RecyclerData>>

    suspend fun addMeal(meal: RecyclerData.Meal)

    suspend fun removeMeal(index: Int)
}
