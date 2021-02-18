package com.example.calorietracker.network

import com.example.calorietracker.data.RecyclerData

data class MealsListResponse(
    val meals: List<Meal> = listOf()
)

data class Meal(
    val calories: String,
    val date: String,
    val id: String,
    val name: String,
    val url: String,
    val weight: String
)

fun MealsListResponse.mapToUiModel(): List<RecyclerData.Meal> {
    val mappedMeals = mutableListOf<RecyclerData.Meal>()
    this.meals.forEach {
        mappedMeals.add(
            RecyclerData.Meal(
                id = it.id,
                mealName = it.name,
                imageUrl = it.url,
                mealWeight = it.weight.toFloat(),
                mealCalories = it.calories.toFloat()
            )
        )
    }
    return mappedMeals
}
