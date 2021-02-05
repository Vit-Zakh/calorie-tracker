package com.example.calorietracker.extensions

import com.example.calorietracker.data.RecyclerData

fun RecyclerData.Food.mapToMeal(weight: Float): RecyclerData.Meal {
    return RecyclerData.Meal(
        id = this.id,
        mealName = this.name,
        imageUrl = this.imageUrl,
        mealCalories = this.calories.times(weight).div(100),
        mealWeight = weight
    )
}
