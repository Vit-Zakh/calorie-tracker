package com.example.calorietracker.extensions

import com.example.calorietracker.models.DomainModel.*
import com.example.calorietracker.models.UiModel

fun Food.mapToUiModel(): UiModel.Food {
    return UiModel.Food(
        id = id,
        name = name,
        imageUrl = url,
        calories = calories.toFloat()
    )
}

fun Meal.mapToUiModel(): UiModel.Meal {
    return UiModel.Meal(
        id = id,
        mealName = name,
        imageUrl = url,
        mealWeight = weight.toFloat(),
        mealCalories = calories.toFloat()
    )
}

