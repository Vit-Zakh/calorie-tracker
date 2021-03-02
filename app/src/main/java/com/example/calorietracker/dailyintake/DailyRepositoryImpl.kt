package com.example.calorietracker.dailyintake

import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.network.NetworkResponse.*
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserState
import com.example.calorietracker.utils.Operations
import kotlinx.coroutines.flow.StateFlow

class DailyRepositoryImpl(
    private val mealsState: MealsState,
    private val userState: UserState,
    private val apiService: TrackerApiService
) : DailyIntakeRepository {

    override val user: StateFlow<UserState.FetchedUserState> = userState.cashedUser
    override val meals: StateFlow<MealsState.FetchedMealsState> = mealsState.cashedMealsList

    override suspend fun refreshState() {
        userState.startFetching()
        mealsState.startFetching()
        userState.refreshUser(apiService.getUser())
        mealsState.refreshMealsList(apiService.getMeals())
    }

    override fun addMeal(mealProps: DailyIntakeProps.MealProps) {
        userState.changeProgress(mealProps, Operations.ADDITION)
        mealsState.addMeal(mealProps)
    }

    override fun deleteMeal(index: Int) {
        userState.changeProgress(meals.value.mealsList[index].mapToUiModel(), Operations.SUBTRACTION)
        mealsState.deleteMeal(index)
    }
}
