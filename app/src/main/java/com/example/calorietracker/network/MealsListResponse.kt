package com.example.calorietracker.network

import com.example.calorietracker.data.RecyclerData

data class MealsListResponse(
    val meals: List<RecyclerData.Meal>
)
