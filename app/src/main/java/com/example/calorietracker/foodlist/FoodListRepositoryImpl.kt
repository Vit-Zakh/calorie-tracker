package com.example.calorietracker.foodlist

import com.example.calorietracker.models.network.FoodResponse
import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.network.TrackerApiService
import com.example.calorietracker.state.FoodListState
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserState
import kotlinx.coroutines.flow.StateFlow

class FoodListRepositoryImpl(
    private val userState: UserState,
    private val foodListState: FoodListState,
    private val mealsState: MealsState,
    private val apiService: TrackerApiService
) : FoodListRepository {

    override val user: StateFlow<UserResponse> = userState.cashedUser
    override val food: StateFlow<List<FoodResponse>> = foodListState.cashedFoodList

    override fun addFood(food: FoodProps) {
        foodListState.addFood(food)
    }

    override fun deleteFood(index: Int) {
        foodListState.deleteFood(index)
    }

    override fun addMealToList(mealProps: DailyIntakeProps.MealProps) {
        mealsState.addMeal(mealProps)
    }

    override suspend fun refreshFood() {
        foodListState.refreshFoodList(apiService.getFoodList())
    }

    override suspend fun refreshUser() {
        userState.refreshUser(apiService.getUser())
    }
}
