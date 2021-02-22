package com.example.calorietracker.models.network

data class MealsListResponse(
    val meals: List<MealResponse> = listOf()
)
