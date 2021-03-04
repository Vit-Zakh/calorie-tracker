package com.example.calorietracker.dailyintake

import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

interface DailyIntakeRepository {

    val user: StateFlow<UserDataSource.UserState>
    val meals: StateFlow<MealsState.FetchedMealsState>

    fun addMeal(mealProps: DailyIntakeProps.MealProps)

    fun deleteMeal(index: Int)

    suspend fun refreshState()
}
