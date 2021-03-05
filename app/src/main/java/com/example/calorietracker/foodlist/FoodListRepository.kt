package com.example.calorietracker.foodlist

import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.state.FoodListDataSource
import com.example.calorietracker.state.UserDataSource
import kotlinx.coroutines.flow.StateFlow

interface FoodListRepository {

    val user: StateFlow<UserDataSource.UserState>
    val food: StateFlow<FoodListDataSource.FoodState>

    fun addFood(food: FoodProps)

    fun deleteFood(index: Int)

    fun addMealToList(mealProps: DailyIntakeProps.MealProps)

    suspend fun refreshFood()

    suspend fun refreshUser()

    /** Test functions block */

    fun showEmptyList()

    fun showFailedList()

    /** End of test functions block */
}
