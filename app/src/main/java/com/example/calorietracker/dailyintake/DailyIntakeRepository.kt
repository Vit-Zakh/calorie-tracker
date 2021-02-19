package com.example.calorietracker.dailyintake

import com.example.calorietracker.models.DomainModel
import com.example.calorietracker.models.UiModel
import com.example.calorietracker.network.UserResponse
import kotlinx.coroutines.flow.StateFlow

interface DailyIntakeRepository {

    val user: StateFlow<UserResponse>
    val meals: StateFlow<List<DomainModel.Meal>>

    fun addMeal(meal: UiModel.Meal)

    fun deleteMeal(index: Int)

    suspend fun refreshState()
}
