package com.example.calorietracker.dailyintake

import android.content.SharedPreferences
import android.util.Log
import com.example.calorietracker.models.network.mapToUiModel
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.network.ApiService
import com.example.calorietracker.state.MealsDataSource
import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

class DailyRepositoryImpl(
    private val mealsDataSource: MealsDataSource,
    private val userDataSource: UserDataSource,
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
) : DailyIntakeRepository {

    override val user: StateFlow<UserDataSource.UserState> = userDataSource.userFlow
    override val meals: StateFlow<MealsDataSource.MealsState> = mealsDataSource.mealsListFlow

    override suspend fun refreshState() {
        mealsDataSource.setLoadingState()
        userDataSource.setLoadingState()
        mealsDataSource.refreshMealsList(apiService.getMeals())
        if (!sharedPreferences.contains("USER_NAME")) {
            userDataSource.refreshUser(apiService.getUser())
        } else userDataSource.getCachedUser(
            userName = sharedPreferences.getString("USER_NAME", ""),
            userWeight = sharedPreferences.getString("USER_WEIGHT", ""),
            userAge = sharedPreferences.getString("USER_AGE", ""),
            userIncome = sharedPreferences.getString("USER_INCOME", ""),
            userImageUrl = sharedPreferences.getString("USER_IMAGE_URL", ""),
            userBackgroundUrl = sharedPreferences.getString("USER_BACKGROUND_URL", "")
        )
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

    override fun saveToSharedPreferences(userName: String, userWeight: String, userAge: String, userIncome: String, userImageUrl: String, userBackgroundUrl: String) {
        sharedPreferences.edit()
            .putString("USER_NAME", userName)
            .putString("USER_WEIGHT", userWeight)
            .putString("USER_AGE", userAge)
            .putString("USER_INCOME", userIncome)
            .putString("USER_IMAGE_URL", userImageUrl)
            .putString("USER_BACKGROUND_URL", userBackgroundUrl)
            .apply()

        Log.d("SHARED_TAG", "saveToSharedPreferences: ${sharedPreferences.getString("USER_NAME", null)}")
    }
}
