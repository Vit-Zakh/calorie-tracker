package com.example.calorietracker.network

import com.example.calorietracker.data.RecyclerData

data class FoodListResponse(
    val food: List<RecyclerData.Food>
)
