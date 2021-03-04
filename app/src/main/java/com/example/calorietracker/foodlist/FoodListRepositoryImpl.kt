package com.example.calorietracker.foodlist

import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.network.ApiService
import com.example.calorietracker.state.FoodListDataSource
import com.example.calorietracker.state.MealsState
import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

class FoodListRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val foodListDataSource: FoodListDataSource,
    private val mealsState: MealsState,
    private val apiService: ApiService
) : FoodListRepository {

    override val user: StateFlow<UserDataSource.UserState> = userDataSource.cashedUser
    override val food: StateFlow<FoodListDataSource.FoodState> = foodListDataSource.cashedFoodList

    override fun addFood(food: FoodProps) {
        foodListDataSource.addFood(food)
    }

    override fun deleteFood(index: Int) {
        foodListDataSource.deleteFood(index)
    }

    override fun addMealToList(mealProps: DailyIntakeProps.MealProps) {

        userDataSource.startFetching()

        userDataSource.increaseCalories(mealProps)
        mealsState.addMeal(mealProps)
    }

    override suspend fun refreshFood() {
        foodListDataSource.startFetching()
        foodListDataSource.refreshFoodList(apiService.getFoodList())
    }

    override suspend fun refreshUser() {
//        userState.startFetching()
//        userState.refreshUser(apiService.getUser())
    }
}
