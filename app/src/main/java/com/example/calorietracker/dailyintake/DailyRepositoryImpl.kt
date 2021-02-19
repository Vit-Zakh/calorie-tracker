package com.example.calorietracker.dailyintake

import com.example.calorietracker.models.DomainModel
import com.example.calorietracker.models.UiModel
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.network.UserResponse
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserState
import kotlinx.coroutines.flow.StateFlow

class DailyRepositoryImpl(
    private val mealsState: MealsState,
    private val userState: UserState,
    private val apiService: TrackerApiService
) : DailyIntakeRepository {

    override val user: StateFlow<UserResponse> = userState.cashedUser
    override val meals: StateFlow<List<DomainModel.Meal>> = mealsState.cashedMealsList

    override suspend fun refreshState() {
        userState.refreshUser(apiService.getUser())
        mealsState.refreshMealsList(apiService.getMeals())
    }

    override fun addMeal(meal: UiModel.Meal) {
        mealsState.addMeal(meal)
    }

    override fun deleteMeal(index: Int) {
        mealsState.deleteMeal(index)
    }
}
