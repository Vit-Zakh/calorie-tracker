package com.example.calorietracker.dailyintake

import com.example.calorietracker.models.network.MealResponse
import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import kotlinx.coroutines.flow.StateFlow

interface DailyIntakeRepository {

    val user: StateFlow<UserResponse>
    val meals: StateFlow<List<MealResponse>>

    fun addMeal(mealProps: DailyIntakeProps.MealProps)

    fun deleteMeal(index: Int)

    suspend fun refreshState()
}
