package com.example.calorietracker.network

import com.example.calorietracker.models.DomainModel

data class FoodListResponse(
    val food: MutableList<DomainModel.Food> = mutableListOf()
)
