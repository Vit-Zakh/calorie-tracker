package com.example.calorietracker.dailyintake

import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.network.ApiService
import com.example.calorietracker.state.MealsDataSource
import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

class DailyRepositoryImpl(
    private val mealsDataSource: MealsDataSource,
    private val userDataSource: UserDataSource,
    private val apiService: ApiService
) : DailyIntakeRepository {

    override val user: StateFlow<UserDataSource.UserState> = userDataSource.userFlow
    override val meals: StateFlow<MealsDataSource.MealsState> = mealsDataSource.mealsListFlow

    override suspend fun refreshState() {
        userDataSource.setLoadingState()
        mealsDataSource.setLoadingState()
        userDataSource.refreshUser(apiService.getUser())
        mealsDataSource.refreshMealsList(apiService.getMeals())
    }

    override fun addMeal(mealProps: DailyIntakeProps.MealProps) {
        userDataSource.setLoadingState()
        userDataSource.increaseCalories(mealProps)
        mealsDataSource.addMeal(mealProps)
    }

    override fun deleteMeal(index: Int) {
        userDataSource.setLoadingState()
        userDataSource.decreaseCalories(meals.value.mealsList[index].mapToUiModel())
        mealsDataSource.deleteMeal(index)
    }
}
