package com.example.calorietracker.foodlist

import com.example.calorietracker.models.network.FoodResponse
import com.example.calorietracker.models.network.UserResponse
import com.example.calorietracker.models.ui.DailyIntakeProps
import com.example.calorietracker.models.ui.FoodProps
import kotlinx.coroutines.flow.StateFlow

interface FoodListRepository {

    val user: StateFlow<UserResponse>
    val food: StateFlow<List<FoodResponse>>

    fun addFood(food: FoodProps)

    fun deleteFood(index: Int)

    fun addMealToList(mealProps: DailyIntakeProps.MealProps)

    suspend fun refreshFood()

    suspend fun refreshUser()
}
