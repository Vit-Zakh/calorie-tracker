package com.example.calorietracker.foodlist

import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.network.ApiService
import com.example.calorietracker.state.FoodListDataSource
import com.example.calorietracker.state.MealsDataSource
import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

class FoodListRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val foodListDataSource: FoodListDataSource,
    private val mealsDataSource: MealsDataSource,
    private val apiService: ApiService
) : FoodListRepository {

    override val user: StateFlow<UserDataSource.UserState> = userDataSource.userFlow
    override val food: StateFlow<FoodListDataSource.FoodState> = foodListDataSource.foodListFlow

    override fun addFood(food: FoodProps) {
        foodListDataSource.addFood(food)
        userDataSource.showLoadedUser()
    }

    override fun deleteFood(index: Int) {
        foodListDataSource.deleteFood(index)
    }

    override fun addMealToList(mealProps: DailyIntakeProps.MealProps) {

        userDataSource.setLoadingState()

        userDataSource.increaseCalories(mealProps)
        mealsDataSource.addMeal(mealProps)
    }

    override suspend fun refreshFood() {
        foodListDataSource.setLoadingState()
        foodListDataSource.refreshFoodList(apiService.getFoodList())
    }

    override suspend fun refreshUser() {
//        userState.startFetching()
//        userState.refreshUser(apiService.getUser())
    }

    /** Test functions block */

    override fun showEmptyList() {
        foodListDataSource.showEmptyList()
    }

    override fun showFailedList() {
        foodListDataSource.showFailedList()
        userDataSource.showFailedUser()
    }

    /** End of test functions block */
}
