package com.example.calorietracker.models.network

import com.example.calorietracker.models.ui.DailyIntakeProps

data class MealResponse(
    val calories: String,
    val date: String,
    val id: String,
    val name: String,
    val url: String,
    val weight: String
)

fun MealResponse.mapToUiModel(): DailyIntakeProps.MealProps {
    return DailyIntakeProps.MealProps(
        id = id,
        mealName = name,
        imageUrl = url,
        mealWeight = weight.toFloat(),
        mealCalories = calories.toFloat()
    )
}
