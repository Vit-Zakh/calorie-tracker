package com.example.calorietracker.utils

import com.example.calorietracker.RecyclerData

object MealMapper {

    fun mapToMeal(food: RecyclerData.Food, weight: Float): RecyclerData.Meal {
        return RecyclerData.Meal(
            id = food.id,
            mealName = food.name,
            imageUrl = food.imageUrl,
            mealCalories = food.calories.times(weight).div(100),
            mealWeight = weight
        )
    }
}
