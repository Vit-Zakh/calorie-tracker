package com.example.calorietracker.dailyintake

import com.example.calorietracker.models.network.MealResponse
import com.example.calorietracker.models.network.UserResponse
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

    override val user: StateFlow<UserResponse> = userState.cashedUser
    override val meals: StateFlow<List<MealResponse>> = mealsState.cashedMealsList

    override suspend fun refreshState() {
//        userState.refreshUser(apiService.getUser())
//        mealsState.refreshMealsList(apiService.getMeals())

//        when (val fetchedUser = apiService.getUser()) {
//            is Success -> {
//                userState.refreshUser(fetchedUser.body)
//                Log.d("TAG", "refreshState: SUCCESS")
//            }
//            is ApiError -> throw RuntimeException(fetchedUser.code.toString())
//            is NetworkError -> throw RuntimeException(fetchedUser.error)
//            is UnknownError -> throw RuntimeException(fetchedUser.error)
//        }
//
//        when (val fetchedMeals = apiService.getMeals()) {
//            is Success -> {
//                mealsState.refreshMealsList(fetchedMeals.body)
//                Log.d("TAG", "refreshState: SUCCESS")
//            }
//            is ApiError -> throw RuntimeException(fetchedMeals.body)
//            is NetworkError -> throw RuntimeException(fetchedMeals.error)
//            is UnknownError -> throw RuntimeException(fetchedMeals.error)
//        }
    }

    override fun addMeal(mealProps: DailyIntakeProps.MealProps) {
        userState.changeProgress(mealProps, Operations.ADDITION)
        mealsState.addMeal(mealProps)
    }

    override fun deleteMeal(index: Int) {
        userState.changeProgress(meals.value[index].mapToUiModel(), Operations.SUBTRACTION)
        mealsState.deleteMeal(index)
    }
}
