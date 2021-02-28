package com.example.calorietracker.foodlist

import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodProps
import com.example.calorietracker.state.FoodListState
import com.example.calorietracker.state.UserState
import kotlinx.coroutines.flow.StateFlow

interface FoodListRepository {

    val user: StateFlow<UserState.FetchedUserState>
    val food: StateFlow<FoodListState.FetchedFoodState>

    fun addFood(food: FoodProps)

    fun deleteFood(index: Int)

    fun addMealToList(mealProps: DailyIntakeProps.MealProps)

    suspend fun refreshFood()

    suspend fun refreshUser()
}
